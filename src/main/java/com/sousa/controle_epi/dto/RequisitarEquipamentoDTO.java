package com.sousa.controle_epi.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Data
@NoArgsConstructor
public class RequisitarEquipamentoDTO {
    private String nomeEquipamento;
    private String numeroCA;
    private LocalDate dataValidade;
}
