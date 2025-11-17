package com.sousa.controle_epi.dto;

import com.sousa.controle_epi.entity.ColaboradorEntity;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class InfosColaboradorDTO {
    private Long id;
    private String nome;
    private String matricula;

    // Construtor que converte Entity para DTO
    public InfosColaboradorDTO(ColaboradorEntity entity) {
        this.id = entity.getId();
        this.nome = entity.getNome();
        this.matricula = entity.getMatricula();
    }
}
