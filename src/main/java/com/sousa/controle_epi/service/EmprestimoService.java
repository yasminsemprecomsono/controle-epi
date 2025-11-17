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

    public List<InfosEmprestimoDTO> listarEmprestimos() {
        return emprestimoRepository.findAll()
                .stream()
                .map(InfosEmprestimoDTO::new)
                .collect(Collectors.toList());
    }
    //read id
    public InfosEmprestimoDTO buscarEmprestimoPorId(Long id) {
        EmprestimoEntity emprestimo = emprestimoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Empréstimo não encontrado"));
        return new InfosEmprestimoDTO(emprestimo);
    }

    public InfosEmprestimoDTO criarEmprestimo(RequisitarEmprestimoDTO dto) {

        ColaboradorEntity colaborador = colaboradorRepository.findById(dto.getIdColaborador())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Colaborador não encontrado"));

        EquipamentoEntity equipamento = equipamentoRepository.findById(dto.getIdEquipamento())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Equipamento não encontrado"));

        boolean equipamentoJaEmprestado = emprestimoRepository
                .existsByEquipamentoIdAndStatus(equipamento.getId(), StatusEmprestimo.ATIVO);

        if (equipamentoJaEmprestado) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Este equipamento já está em uso por outro colaborador.");
        }
        EmprestimoEntity novoEmprestimo = new EmprestimoEntity();
        novoEmprestimo.setColaborador(colaborador);
        novoEmprestimo.setEquipamento(equipamento);
        novoEmprestimo.setDataEmprestimo(LocalDate.now());
        novoEmprestimo.setStatus(StatusEmprestimo.ATIVO);

        EmprestimoEntity emprestimoSalvo = emprestimoRepository.save(novoEmprestimo);
        return new InfosEmprestimoDTO(emprestimoSalvo);
    }

    // devolver
    public InfosEmprestimoDTO devolverEquipamento(Long idEmprestimo) {
        EmprestimoEntity emprestimo = emprestimoRepository.findById(idEmprestimo)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Empréstimo não encontrado"));

        // se ja nao foi devolvido
        if (emprestimo.getStatus() == StatusEmprestimo.DEVOLVIDO) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Este equipamento já foi devolvido.");
        }

        // atualizar o status e data
        emprestimo.setStatus(StatusEmprestimo.DEVOLVIDO);
        emprestimo.setDataDevolucao(LocalDate.now());

        EmprestimoEntity emprestimoSalvo = emprestimoRepository.save(emprestimo);
        return new InfosEmprestimoDTO(emprestimoSalvo);
    }
}
