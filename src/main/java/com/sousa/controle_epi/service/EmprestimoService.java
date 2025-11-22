package com.sousa.controle_epi.service;
import com.sousa.controle_epi.dto.InfosEmprestimoDTO;
import com.sousa.controle_epi.dto.RequisitarEmprestimoDTO;
import com.sousa.controle_epi.entity.*;
import com.sousa.controle_epi.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmprestimoService {
    @Autowired private EmprestimoRepository emprestimoRepository;
    @Autowired private ColaboradorRepository colaboradorRepository;
    @Autowired private EquipamentoRepository equipamentoRepository;

    public List<InfosEmprestimoDTO> listarEmprestimos() {
        return emprestimoRepository.findAll().stream().map(InfosEmprestimoDTO::new).collect(Collectors.toList());
    }

    public InfosEmprestimoDTO criarEmprestimo(RequisitarEmprestimoDTO dto) {
        ColaboradorEntity colaborador = colaboradorRepository.findById(dto.getIdColaborador()).get();
        EquipamentoEntity equipamento = equipamentoRepository.findById(dto.getIdEquipamento()).get();

        if (equipamento.getDataValidade() != null && equipamento.getDataValidade().isBefore(LocalDate.now())) {
            throw new RuntimeException("BLOQUEADO: EPI Vencido!");
        }

        if (equipamento.getStatus() == StatusEquipamento.MANUTENCAO) {
            throw new RuntimeException("BLOQUEADO: Item em manutenção.");
        }

        if (equipamento.getStatus() == StatusEquipamento.EMPRESTADO) {
            throw new RuntimeException("BLOQUEADO: Item já está com outra pessoa.");
        }

        if (equipamento.getNomeEquipamento().toLowerCase().contains("alta tensão")) {
            if (!colaborador.getCargo().equalsIgnoreCase("Eletricista")) {
                throw new RuntimeException("BLOQUEADO: Apenas Eletricistas podem retirar.");
            }
        }

        EmprestimoEntity novo = new EmprestimoEntity();
        novo.setColaborador(colaborador);
        novo.setEquipamento(equipamento);
        novo.setDataEmprestimo(LocalDate.now());
        novo.setStatus(StatusEmprestimo.ATIVO);

        equipamento.setStatus(StatusEquipamento.EMPRESTADO);
        equipamentoRepository.save(equipamento);

        return new InfosEmprestimoDTO(emprestimoRepository.save(novo));
    }

    public InfosEmprestimoDTO devolverEquipamento(Long idEmprestimo, boolean estaQuebrado) {
        EmprestimoEntity emprestimo = emprestimoRepository.findById(idEmprestimo).get();
        EquipamentoEntity equipamento = emprestimo.getEquipamento();

        if (emprestimo.getStatus() == StatusEmprestimo.DEVOLVIDO) {
            throw new RuntimeException("Já devolvido!");
        }

        if (estaQuebrado) {
            equipamento.setStatus(StatusEquipamento.MANUTENCAO);
            System.out.println("EPI quebrado, enviado para manutenção.");
        } else {
            equipamento.setStatus(StatusEquipamento.DISPONIVEL);
        }
        equipamentoRepository.save(equipamento);
        emprestimo.setStatus(StatusEmprestimo.DEVOLVIDO);
        emprestimo.setDataDevolucao(LocalDate.now());

        return new InfosEmprestimoDTO(emprestimoRepository.save(emprestimo));
    }

    public InfosEmprestimoDTO buscarEmprestimoPorId(Long id) {
        return new InfosEmprestimoDTO(emprestimoRepository.findById(id).get());
    }
}