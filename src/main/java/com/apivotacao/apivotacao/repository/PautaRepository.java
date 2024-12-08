package com.apivotacao.apivotacao.repository;

import com.apivotacao.apivotacao.model.entity.Pauta;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public interface PautaRepository extends JpaRepository<Pauta, Long> {
}
