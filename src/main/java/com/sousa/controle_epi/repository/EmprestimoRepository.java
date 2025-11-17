package com.sousa.controle_epi.repository;

import com.sousa.controle_epi.entity.EmprestimoEntity;
import com.sousa.controle_epi.entity.StatusEmprestimo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmprestimoRepository extends JpaRepository<EmprestimoEntity, Long> {

    boolean existsByEquipamentoIdAndStatus(Long equipamentoId, StatusEmprestimo status);
}