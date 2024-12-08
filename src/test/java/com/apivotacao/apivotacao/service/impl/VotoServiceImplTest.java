package com.apivotacao.apivotacao.service.impl;

import static org.junit.jupiter.api.Assertions.*;

import com.apivotacao.apivotacao.model.entity.Associado;
import com.apivotacao.apivotacao.model.entity.Sessao;
import com.apivotacao.apivotacao.model.entity.Voto;
import com.apivotacao.apivotacao.model.enuns.OpcaoVoto;
import com.apivotacao.apivotacao.repository.VotoRepository;
import com.apivotacao.apivotacao.service.SessaoService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.mockito.Mockito.*;

import java.util.Date;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class VotoServiceImplTest {

    @Mock
    private VotoRepository repository;

    @Mock
    private SessaoService sessaoService;

    @InjectMocks
    private VotoServiceImpl votoService;

    @Test
    void deveSalvarVotoQuandoValido() {
        Associado associado = new Associado();
        associado.setId(1);
        associado.setNome("Associado Teste");

        Sessao sessao = new Sessao();
        sessao.setId(1L);

        Voto voto = new Voto();
        voto.setAssociado(associado);
        voto.setSessao(sessao);
        voto.setOpcaoVoto(OpcaoVoto.SIM);
        voto.setDataVoto(new Date());

        when(sessaoService.isSessaoAtiva(sessao.getId())).thenReturn(true);
        when(repository.save(voto)).thenReturn(voto);

        Voto result = votoService.vote(voto);

        assertNotNull(result);
        assertEquals(voto.getAssociado(), result.getAssociado());
        assertEquals(voto.getSessao(), result.getSessao());
        assertEquals(voto.getOpcaoVoto(), result.getOpcaoVoto());
        verify(repository, times(1)).save(voto);
    }

    @Test
    void deveRetornarErroAoSalvarVotoComSessaoNula() {
        Associado associado = new Associado();
        Sessao sessao = null;

        Voto voto = new Voto();
        voto.setAssociado(associado);
        voto.setSessao(sessao);
        voto.setOpcaoVoto(OpcaoVoto.SIM);
        voto.setDataVoto(new Date());

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            votoService.vote(voto);
        });

        assertEquals("O voto deve receber uma Sessão existente!", exception.getMessage());
        verify(repository, times(0)).save(voto);
    }

    @Test
    void deveRetornarErroAoTentarSalvarVotoEmSessaoInativa() {
        Associado associado = new Associado();
        Sessao sessao = new Sessao();
        sessao.setId(1L);

        Voto voto = new Voto();
        voto.setAssociado(associado);
        voto.setSessao(sessao);
        voto.setOpcaoVoto(OpcaoVoto.SIM);
        voto.setDataVoto(new Date());

        when(sessaoService.isSessaoAtiva(sessao.getId())).thenReturn(false);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            votoService.vote(voto);
        });

        assertEquals("O voto deve ser enviado para uma Sessão ativa!", exception.getMessage());
        verify(repository, times(0)).save(voto);
    }

    @Test
    void deveRetornarErroAoSalvarVotoComUmAssociadoInvalidowhenAssociado() {
        Sessao sessao = new Sessao();
        sessao.setId(1L);

        Voto voto = new Voto();
        voto.setAssociado(null);
        voto.setSessao(sessao);
        voto.setOpcaoVoto(OpcaoVoto.SIM);
        voto.setDataVoto(new Date());

        when(sessaoService.isSessaoAtiva(sessao.getId())).thenReturn(true);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            votoService.vote(voto);
        });

        assertEquals("O voto deve receber um Associado válido!", exception.getMessage());
        verify(repository, times(0)).save(voto);
    }
}