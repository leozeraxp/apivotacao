package com.apivotacao.apivotacao.service.impl;

import com.apivotacao.apivotacao.model.entity.Pauta;
import com.apivotacao.apivotacao.repository.PautaRepository;
import jakarta.transaction.Transactional;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class PautaServiceImplTest {

    @Mock
    private PautaRepository repository;

    @InjectMocks
    private PautaServiceImpl pautaService;


    @Test
    void deveSalvarPautaValida() {
        Pauta pauta = new Pauta();
        pauta.setId(1L);
        pauta.setDescricao("Descrição válida da pauta");
        when(repository.save(pauta)).thenReturn(pauta);

        Pauta result = pautaService.create(pauta);

        assertNotNull(result);
        assertEquals(pauta.getDescricao(), result.getDescricao());
        assertNotNull(result.getCriacao());

        verify(repository, times(1)).save(pauta);
    }

    @Test
    void deveRetornarErroAoTentarCriarPautaComDescricaoInvalida() {
        Pauta pauta = new Pauta();
        pauta.setId(1L);
        pauta.setDescricao(null);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            pautaService.create(pauta);
        });

        assertEquals("A pauta deve possuir uma descrição!", exception.getMessage());
        verify(repository, times(0)).save(pauta);
    }

    @Test
    void deveRetornarVerdadeiroAoBuscarPautaQueExiste() {
        when(repository.existsById(1L)).thenReturn(true);

        assertTrue(pautaService.existsById(1L));

        verify(repository, times(1)).existsById(1L);
    }

    @Test
    void deveRetornarFalsoAoBuscarPautaQueNaoExiste() {
        when(repository.existsById(1L)).thenReturn(false);

        assertFalse(pautaService.existsById(1L));

        verify(repository, times(1)).existsById(1L);
    }

}