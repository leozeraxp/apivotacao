package com.apivotacao.apivotacao.repository;

import com.apivotacao.apivotacao.model.entity.Associado;
import com.apivotacao.apivotacao.model.entity.Pauta;
import com.apivotacao.apivotacao.model.entity.Resultado;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class ResultadoRepositoryTest {

    @Autowired
    private AssociadoRepository associadoRepository;

    @Autowired
    private PautaRepository pautaRepository;

    @Autowired
    private ResultadoRepository resultadoRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private SessaoRepository sessaoRepository;

    @Test
    public void deveBuscarPrimeiraPautaPorId() {
        Pauta pauta = new Pauta();
        pauta.setCriacao(LocalDateTime.now());
        pauta.setDescricao("Teste Resultado");
        pautaRepository.save(pauta);

        Resultado resultado = new Resultado();
        resultado.setPauta(pauta);
        resultado.setTotalVotos(10L);
        resultado.setTotalSim(6L);
        resultado.setTotalNao(4L);
        resultadoRepository.save(resultado);


        Resultado expected = new Resultado();
        expected.setPauta(pauta);
        expected.setTotalVotos(10L);
        expected.setTotalSim(6L);
        expected.setTotalNao(4L);

        Optional<Resultado> result = resultadoRepository.findFirstByPautaId(pauta.getId());

        assertTrue(result.isPresent());

        assertEquals(expected.getTotalSim(), result.get().getTotalSim());
        assertEquals(expected.getTotalNao(), result.get().getTotalNao());
        assertEquals(expected.getTotalVotos(), result.get().getTotalVotos());
        assertEquals(expected.getPauta().getId(), result.get().getPauta().getId());
    }

    @Test
    public void deveRetornarOptionalVazioAoBuscarPautaInexistente() {
        Optional<Resultado> result = resultadoRepository.findFirstByPautaId(0L);

        assertTrue(result.isEmpty());
    }

}