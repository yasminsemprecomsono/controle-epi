package com.sousa.controle_epi.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "colaboradores")
@Data // Anotação do Lombok para getters, setters, equals, hashCode e toString
@NoArgsConstructor // Construtor vazio
@AllArgsConstructor // Construtor com todos os argumentos
public class ColaboradorEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false, unique = true)
    private String matricula;

    // Um colaborador pode ter vários empréstimos
    @OneToMany(mappedBy = "colaborador")
    private List<EmprestimoEntity> emprestimos;
}
