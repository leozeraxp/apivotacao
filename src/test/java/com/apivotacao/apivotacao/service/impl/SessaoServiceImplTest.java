package com.apivotacao.apivotacao.service.impl;

import com.apivotacao.apivotacao.model.entity.Pauta;
import com.apivotacao.apivotacao.model.entity.Sessao;
import com.apivotacao.apivotacao.repository.SessaoRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@SpringBootTest
@ActiveProfiles("test")
@Transactional
class SessaoServiceImplTest {
    @Mock
    private SessaoRepository repository;

    @InjectMocks
    private SessaoServiceImpl sessaoService;

    @Test
    void deveCriarSessaoValida() {
        Pauta pauta = new Pauta();
        pauta.setId(1L);
        pauta.setDescricao("Descrição da pauta");

        Sessao sessao = new Sessao();
        sessao.setId(1L);
        sessao.setPauta(pauta);
        sessao.setInicio(LocalDateTime.now());

        when(repository.save(sessao)).thenReturn(sessao);

        Sessao result = sessaoService.create(sessao);

        assertNotNull(result);
        assertEquals(sessao.getPauta(), result.getPauta());
        assertNotNull(result.getInicio());
        assertNotNull(result.getFim());

        verify(repository, times(1)).save(sessao);
    }

    @Test
    void deveRetornarErroAoTentarCriarSessaoComPautaNula() {
        Sessao sessao = new Sessao();
        sessao.setId(1L);
        sessao.setInicio(LocalDateTime.now());
        sessao.setPauta(null);


        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            sessaoService.create(sessao);
        });

        assertEquals("A sessão deve receber uma Pauta para ser iniciada!", exception.getMessage());

        verify(repository, times(0)).save(sessao);
    }

    @Test
    void deveRetornarVerdadeiroAoBuscarSessaoValidaAtiva() {
        Pauta pauta = new Pauta();
        pauta.setId(1L);
        pauta.setDescricao("Descrição da pauta");

        Sessao sessao = new Sessao();
        sessao.setId(1L);
        sessao.setPauta(pauta);
        sessao.setInicio(LocalDateTime.now());
        sessao.setFim(LocalDateTime.now().plusMinutes(10));

        when(repository.getReferenceById(sessao.getId())).thenReturn(sessao);

        boolean isAtiva = sessaoService.isSessaoAtiva(sessao.getId());

        assertTrue(isAtiva);

        verify(repository, times(1)).getReferenceById(sessao.getId());
    }

    @Test
    void deveRetornarFalsoAoBuscarSessaoValidaInativa() {
        Pauta pauta = new Pauta();
        pauta.setId(1L);
        pauta.setDescricao("Descrição da pauta");

        Sessao sessao = new Sessao();
        sessao.setId(1L);
        sessao.setPauta(pauta);
        sessao.setInicio(LocalDateTime.now());
        sessao.setFim(LocalDateTime.now().minusMinutes(10));

        when(repository.getReferenceById(sessao.getId())).thenReturn(sessao);

        boolean isAtiva = sessaoService.isSessaoAtiva(sessao.getId());

        assertFalse(isAtiva);

        verify(repository, times(1)).getReferenceById(sessao.getId());
    }

    @Test
    void deveBuscarSessaoPorIdPauta() {
        Pauta pauta = new Pauta();
        pauta.setId(1L);
        pauta.setDescricao("Descrição da pauta");

        Sessao sessao = new Sessao();
        sessao.setId(1L);
        sessao.setPauta(pauta);
        sessao.setInicio(LocalDateTime.now());

        when(repository.findByPautaId(pauta.getId())).thenReturn(List.of(sessao));

        List<Sessao> result = sessaoService.findByPautaId(pauta.getId());

        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());

        verify(repository, times(1)).findByPautaId(pauta.getId());
    }
}