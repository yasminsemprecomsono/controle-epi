package com.sousa.controle_epi.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Entity
@Table(name = "colaboradores")
@Data
@NoArgsConstructor
public class ColaboradorEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false, unique = true)
    private String matricula;

    @Column(nullable = false)
    private String cargo;

    @OneToMany(mappedBy = "colaborador")
    private List<EmprestimoEntity> emprestimos;
}