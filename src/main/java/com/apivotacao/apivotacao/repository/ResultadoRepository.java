package com.apivotacao.apivotacao.repository;

import com.apivotacao.apivotacao.model.entity.Resultado;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ResultadoRepository extends JpaRepository<Resultado, Long> {

    Optional<Resultado> findFirstByPautaId(Long pautaId);
}
