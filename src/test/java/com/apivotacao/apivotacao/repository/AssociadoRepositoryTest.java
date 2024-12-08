package com.apivotacao.apivotacao.repository;

import com.apivotacao.apivotacao.model.entity.Associado;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class AssociadoRepositoryTest {

    @Autowired
    private AssociadoRepository associadoRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    public void setUp() {
        jdbcTemplate.execute("DELETE FROM associado");
        jdbcTemplate.execute("DELETE FROM pauta");
        jdbcTemplate.execute("DELETE FROM sessao");
        jdbcTemplate.execute("DELETE FROM voto");
    }

    @Test
    public void deveRetornarVerdadeiroAoVerificarCpfExistente() {
        Associado associado = new Associado();
        associado.setCpf("12345678900");
        associado.setNome("Antônio Manoel");
        associadoRepository.save(associado);

        boolean result = associadoRepository.existsByCpf("12345678900");
        assertTrue(result, "Deveria retornar true, pois o CPF existe");
    }

    @Test
    public void deveRetornarFalsoAoVerificarCpfInexistente() {
        boolean result = associadoRepository.existsByCpf("00000000");
        assertFalse(result, "Deveria retornar falso, pois o CPF existe");
    }
}