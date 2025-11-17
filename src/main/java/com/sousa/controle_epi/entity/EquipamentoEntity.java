package com.sousa.controle_epi.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "equipamentos")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EquipamentoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nomeEquipamento;

    @Column(name = "numero_ca", nullable = false, unique = true) // <--- O SEGREDO TÁ AQUI
    private String numeroCA;
}
