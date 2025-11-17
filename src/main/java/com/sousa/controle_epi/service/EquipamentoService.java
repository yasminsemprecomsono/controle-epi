package com.sousa.controle_epi.service;
import com.sousa.controle_epi.dto.InfosEquipamentoDTO;
import com.sousa.controle_epi.dto.RequisitarEquipamentoDTO;
import com.sousa.controle_epi.entity.EquipamentoEntity;
import com.sousa.controle_epi.repository.EquipamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EquipamentoService {
    @Autowired
    private EquipamentoRepository equipamentoRepository;

    // create
    //equipamento novo no banco
    public InfosEquipamentoDTO criarEquipamento(RequisitarEquipamentoDTO dto) {
        EquipamentoEntity equipamento = new EquipamentoEntity();
        equipamento.setNomeEquipamento(dto.getNomeEquipamento());
        equipamento.setNumeroCA(dto.getNumeroCA());

        EquipamentoEntity equipamentoSalvo = equipamentoRepository.save(equipamento);
        return new InfosEquipamentoDTO(equipamentoSalvo);
    }
//ve todos os equipamentos cadastrados
    public List<InfosEquipamentoDTO> listarEquipamentos() {
        return equipamentoRepository.findAll()
                .stream()
                .map(InfosEquipamentoDTO::new)
                .collect(Collectors.toList());
    }
//pega equipamento pelo id
    public InfosEquipamentoDTO buscarEquipamentoPorId(Long id) {
        EquipamentoEntity equipamento = equipamentoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Equipamento não encontrado"));
        return new InfosEquipamentoDTO(equipamento);
    }
    // up
    //atualiza os dados que ja existem
    public InfosEquipamentoDTO atualizarEquipamento(Long id, RequisitarEquipamentoDTO dto) {
        EquipamentoEntity equipamento = equipamentoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Equipamento não encontrado"));

        equipamento.setNomeEquipamento(dto.getNomeEquipamento());
        equipamento.setNumeroCA(dto.getNumeroCA());
        EquipamentoEntity equipamentoAtualizado = equipamentoRepository.save(equipamento);
        return new InfosEquipamentoDTO(equipamentoAtualizado);
    }
//deleta equipamento
    public void deletarEquipamento(Long id) {
        if (!equipamentoRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Equipamento não encontrado");
        }
        equipamentoRepository.deleteById(id);
    }
}
