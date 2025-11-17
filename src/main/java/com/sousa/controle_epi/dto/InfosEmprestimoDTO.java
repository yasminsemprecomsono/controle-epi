package com.sousa.controle_epi.dto;

import com.sousa.controle_epi.entity.EmprestimoEntity;
import com.sousa.controle_epi.entity.StatusEmprestimo;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
public class InfosEmprestimoDTO {
    private Long idEmprestimo;
    private LocalDate dataEmprestimo;
    private LocalDate dataDevolucao;
    private StatusEmprestimo status;
    private Long idColaborador;
    private String nomeColaborador;
    private Long idEquipamento;
    private String nomeEquipamento;
    private String numeroCAEquipamento;

    public InfosEmprestimoDTO(EmprestimoEntity entity) {
        this.idEmprestimo = entity.getId();
        this.dataEmprestimo = entity.getDataEmprestimo();
        this.dataDevolucao = entity.getDataDevolucao();
        this.status = entity.getStatus();
        this.idColaborador = entity.getColaborador().getId();
        this.nomeColaborador = entity.getColaborador().getNome();
        this.idEquipamento = entity.getEquipamento().getId();
        this.nomeEquipamento = entity.getEquipamento().getNomeEquipamento();
        this.numeroCAEquipamento = entity.getEquipamento().getNumeroCA();
    }
}