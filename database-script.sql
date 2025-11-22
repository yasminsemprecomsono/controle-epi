CREATE DATABASE IF NOT EXISTS controle_epi;
USE controle_epi;

DROP TABLE IF EXISTS emprestimos;
DROP TABLE IF EXISTS equipamentos;
DROP TABLE IF EXISTS colaboradores;

CREATE TABLE colaboradores (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    matricula VARCHAR(255) NOT NULL UNIQUE
);

CREATE TABLE equipamentos (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nome_equipamento VARCHAR(255) NOT NULL,
    numero_ca VARCHAR(255) NOT NULL UNIQUE,
    data_validade DATE
);

CREATE TABLE emprestimos (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    data_emprestimo DATE NOT NULL,
    data_devolucao DATE,
    status VARCHAR(50) NOT NULL,
    colaborador_id BIGINT NOT NULL,
    equipamento_id BIGINT NOT NULL,

    CONSTRAINT fk_emprestimo_colaborador
        FOREIGN KEY (colaborador_id) REFERENCES colaboradores(id),

    CONSTRAINT fk_emprestimo_equipamento
        FOREIGN KEY (equipamento_id) REFERENCES equipamentos(id)
);