package com.sousa.controle_epi.dto;

import com.sousa.controle_epi.entity.EquipamentoEntity;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class InfosEquipamentoDTO {
    private Long id;
    private String nomeEquipamento;
    private String numeroCA;

    public InfosEquipamentoDTO(EquipamentoEntity entity) {
        this.id = entity.getId();
        this.nomeEquipamento = entity.getNomeEquipamento();
        this.numeroCA = entity.getNumeroCA();
    }
}
