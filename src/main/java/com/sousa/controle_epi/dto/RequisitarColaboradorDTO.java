package com.sousa.controle_epi.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RequisitarColaboradorDTO {
    private String nome;
    private String matricula;
    private String cargo;
}