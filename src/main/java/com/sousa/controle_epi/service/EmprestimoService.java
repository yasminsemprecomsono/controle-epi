package com.sousa.controle_epi.service;

import com.sousa.controle_epi.dto.InfosEmprestimoDTO;
import com.sousa.controle_epi.dto.RequisitarEmprestimoDTO;
import com.sousa.controle_epi.entity.ColaboradorEntity;
import com.sousa.controle_epi.entity.EmprestimoEntity;
import com.sousa.controle_epi.entity.EquipamentoEntity;
import com.sousa.controle_epi.entity.StatusEmprestimo;
import com.sousa.controle_epi.repository.ColaboradorRepository;
import com.sousa.controle_epi.repository.EmprestimoRepository;
import com.sousa.controle_epi.repository.EquipamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmprestimoService {

    @Autowired
    private EmprestimoRepository emprestimoRepository;

    @Autowired
    private ColaboradorRepository colaboradorRepository;

    @Autowired
    private EquipamentoRepository equipamentoRepository;

    // READ (All)
    public List<InfosEmprestimoDTO> listarEmprestimos() {
        return emprestimoRepository.findAll()
                .stream()
                .map(InfosEmprestimoDTO::new)
                .collect(Collectors.toList());
    }

    // READ (One by ID)
    public InfosEmprestimoDTO buscarEmprestimoPorId(Long id) {
        EmprestimoEntity emprestimo = emprestimoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Empréstimo não encontrado"));
        return new InfosEmprestimoDTO(emprestimo);
    }

    // CREATE (Ação de "Pegar Emprestado")
    public InfosEmprestimoDTO criarEmprestimo(RequisitarEmprestimoDTO dto) {
        // 1. Validar se o colaborador existe
        ColaboradorEntity colaborador = colaboradorRepository.findById(dto.getIdColaborador())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Colaborador não encontrado"));

        // 2. Validar se o equipamento existe
        EquipamentoEntity equipamento = equipamentoRepository.findById(dto.getIdEquipamento())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Equipamento não encontrado"));

        // 3. REGRA DE NEGÓCIO: Verificar se o equipamento já está emprestado
        boolean equipamentoJaEmprestado = emprestimoRepository
                .existsByEquipamentoIdAndStatus(equipamento.getId(), StatusEmprestimo.ATIVO);

        if (equipamentoJaEmprestado) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Este equipamento já está em uso por outro colaborador.");
        }

        // 4. Criar o empréstimo
        EmprestimoEntity novoEmprestimo = new EmprestimoEntity();
        novoEmprestimo.setColaborador(colaborador);
        novoEmprestimo.setEquipamento(equipamento);
        novoEmprestimo.setDataEmprestimo(LocalDate.now());
        novoEmprestimo.setStatus(StatusEmprestimo.ATIVO);
        // dataDevolucao fica nula

        EmprestimoEntity emprestimoSalvo = emprestimoRepository.save(novoEmprestimo);
        return new InfosEmprestimoDTO(emprestimoSalvo);
    }

    // UPDATE (Ação de "Devolver")
    public InfosEmprestimoDTO devolverEquipamento(Long idEmprestimo) {
        // 1. Encontrar o empréstimo
        EmprestimoEntity emprestimo = emprestimoRepository.findById(idEmprestimo)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Empréstimo não encontrado"));

        // 2. Validar se ele já não foi devolvido
        if (emprestimo.getStatus() == StatusEmprestimo.DEVOLVIDO) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Este equipamento já foi devolvido.");
        }

        // 3. Atualizar o status e a data
        emprestimo.setStatus(StatusEmprestimo.DEVOLVIDO);
        emprestimo.setDataDevolucao(LocalDate.now());

        EmprestimoEntity emprestimoSalvo = emprestimoRepository.save(emprestimo);
        return new InfosEmprestimoDTO(emprestimoSalvo);
    }
}
