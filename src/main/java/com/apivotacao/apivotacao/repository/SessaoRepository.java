package com.apivotacao.apivotacao.repository;

import com.apivotacao.apivotacao.model.entity.Sessao;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Transactional
public interface SessaoRepository extends JpaRepository<Sessao, Long> {

    @Query("SELECT s FROM Sessao s WHERE s.pauta.id = :pautaId")
    List<Sessao> findByPautaId(Long pautaId);
}
