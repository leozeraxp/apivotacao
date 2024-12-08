package com.apivotacao.apivotacao.repository;

import com.apivotacao.apivotacao.model.entity.Voto;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public interface VotoRepository extends JpaRepository<Voto, Long> {
}
