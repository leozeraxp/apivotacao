package com.desafiovagasicredi.model.repository;

import com.desafiovagasicredi.model.entity.Associado;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@Transactional
public interface AssociadoRepository extends JpaRepository<Associado, Integer> {
    public boolean existsByCpf(String cpf);

    public Optional<Associado> findByCpf(String cpf);
}
