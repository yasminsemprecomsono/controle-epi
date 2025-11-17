package com.sousa.controle_epi.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity //"avisa o java" que vai virar uma tabela no banco
@Table(name = "colaboradores") //noem da tabela no banco
@Data // get e set
@NoArgsConstructor //construtor vazio
@AllArgsConstructor // construtor preenchido
public class ColaboradorEntity {

    @Id //id key
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false, unique = true)//aqui nao pode repetir matricula
    private String matricula;

    //o relacionamento acontece aqui onde o colaborador pode ter muitos emprestimos
    @OneToMany(mappedBy = "colaborador")
    private List<EmprestimoEntity> emprestimos;
}
