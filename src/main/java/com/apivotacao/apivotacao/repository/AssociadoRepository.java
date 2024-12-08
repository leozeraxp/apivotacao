package com.apivotacao.apivotacao.repository;

import com.apivotacao.apivotacao.model.entity.Associado;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public interface AssociadoRepository extends JpaRepository<Associado, Long> {

    public boolean existsByCpf(String cpf);
}
