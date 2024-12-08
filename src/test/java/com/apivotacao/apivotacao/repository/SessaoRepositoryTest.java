package com.apivotacao.apivotacao.repository;

import com.apivotacao.apivotacao.model.entity.Pauta;
import com.apivotacao.apivotacao.model.entity.Resultado;
import com.apivotacao.apivotacao.model.entity.Sessao;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class SessaoRepositoryTest {

    @Autowired
    private AssociadoRepository associadoRepository;

    @Autowired
    private PautaRepository pautaRepository;

    @Autowired
    private SessaoRepository sessaoRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;


    @Test
    public void devRetornarListaVazioAoBuscarSessaoSemPautaExistente(){
        List<Sessao> result = sessaoRepository.findByPautaId(0L);

        assertEquals(0, result.size());
    }

}