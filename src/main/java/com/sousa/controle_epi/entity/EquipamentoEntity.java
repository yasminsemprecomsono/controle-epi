package com.sousa.controle_epi.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Entity
@Table(name = "equipamentos")
@Data
@NoArgsConstructor
public class EquipamentoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nomeEquipamento;

    @Column(name = "numero_ca", nullable = false, unique = true)
    private String numeroCA;

    private LocalDate dataValidade;

    @Enumerated(EnumType.STRING)
    private StatusEmprestimo status = StatusEmprestimo.ATIVO;
}