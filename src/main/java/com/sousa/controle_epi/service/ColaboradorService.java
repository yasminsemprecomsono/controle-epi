package com.sousa.controle_epi.service;

import com.sousa.controle_epi.dto.InfosColaboradorDTO;
import com.sousa.controle_epi.dto.RequisitarColaboradorDTO;
import com.sousa.controle_epi.entity.ColaboradorEntity;
import com.sousa.controle_epi.repository.ColaboradorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ColaboradorService {

    @Autowired
    private ColaboradorRepository colaboradorRepository;

    // create
    public InfosColaboradorDTO criarColaborador(RequisitarColaboradorDTO dto) {
        ColaboradorEntity colaborador = new ColaboradorEntity();
        colaborador.setNome(dto.getNome());
        colaborador.setMatricula(dto.getMatricula());

        ColaboradorEntity colaboradorSalvo = colaboradorRepository.save(colaborador);
        return new InfosColaboradorDTO(colaboradorSalvo);
    }
    public List<InfosColaboradorDTO> listarColaboradores() {
        return colaboradorRepository.findAll()
                .stream()
                .map(InfosColaboradorDTO::new) // Converte cada entidade para DTO
                .collect(Collectors.toList());
    }

    // read
    public InfosColaboradorDTO buscarColaboradorPorId(Long id) {
        ColaboradorEntity colaborador = colaboradorRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Colaborador não encontrado"));
        return new InfosColaboradorDTO(colaborador);
    }

    // update
    public InfosColaboradorDTO atualizarColaborador(Long id, RequisitarColaboradorDTO dto) {
        ColaboradorEntity colaborador = colaboradorRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Colaborador não encontrado"));

        colaborador.setNome(dto.getNome());
        colaborador.setMatricula(dto.getMatricula());

        ColaboradorEntity colaboradorAtualizado = colaboradorRepository.save(colaborador);
        return new InfosColaboradorDTO(colaboradorAtualizado);
    }

    // delete
    public void deletarColaborador(Long id) {
        if (!colaboradorRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Colaborador não encontrado");
        }
        colaboradorRepository.deleteById(id);
    }
}