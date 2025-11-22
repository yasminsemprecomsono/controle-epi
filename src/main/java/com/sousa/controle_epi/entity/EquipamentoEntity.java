package com.sousa.controle_epi.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import com.sousa.controle_epi.entity.StatusEquipamento;

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
    private StatusEquipamento status = StatusEquipamento.DISPONIVEL;
}