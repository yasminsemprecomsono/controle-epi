package com.sousa.controle_epi.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "emprestimos")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmprestimoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Muitos empréstimos podem pertencer a UM colaborador
    @ManyToOne
    @JoinColumn(name = "colaborador_id", nullable = false)
    private ColaboradorEntity colaborador;

    // Muitos empréstimos podem ser do MESMO equipamento (em datas diferentes)
    @ManyToOne
    @JoinColumn(name = "equipamento_id", nullable = false)
    private EquipamentoEntity equipamento;

    @Column(nullable = false)
    private LocalDate dataEmprestimo;

    private LocalDate dataDevolucao; // Fica nulo até ser devolvido

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusEmprestimo status;
}