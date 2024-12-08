package com.apivotacao.apivotacao.service.impl;

import com.apivotacao.apivotacao.model.entity.Pauta;
import com.apivotacao.apivotacao.model.entity.Resultado;
import com.apivotacao.apivotacao.model.entity.Sessao;
import com.apivotacao.apivotacao.model.entity.Voto;
import com.apivotacao.apivotacao.model.enuns.OpcaoVoto;
import com.apivotacao.apivotacao.repository.ResultadoRepository;
import com.apivotacao.apivotacao.service.PautaService;
import com.apivotacao.apivotacao.service.SessaoService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class ResultadoServiceImplTest {


    @Mock
    private ResultadoRepository repository;

    @Mock
    private SessaoService sessaoService;

    @Mock
    private PautaService pautaService;

    @InjectMocks
    private ResultadoServiceImpl resultadoService;

    @Test
    void deveObterResultadoPorIdPauta() {
        Long idPauta = 1L;

        Resultado resultado = new Resultado();
        resultado.setId(1L);
        resultado.setTotalVotos(10L);
        resultado.setTotalSim(6L);
        resultado.setTotalNao(4L);

        when(repository.findFirstByPautaId(idPauta)).thenReturn(Optional.of(resultado));

        Resultado result = resultadoService.getResultado(idPauta);

        assertNotNull(result);
        assertEquals(resultado.getId(), result.getId());
        assertEquals(resultado.getTotalVotos(), result.getTotalVotos());
        verify(repository, times(1)).findFirstByPautaId(idPauta);
    }

    @Test
    void deveRetornarErroAoBuscarResultadoComPautaInvalida() {
        Long idPauta = 1L;

        when(repository.findFirstByPautaId(idPauta)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            resultadoService.getResultado(idPauta);
        });

        assertEquals("Resultado para Pauta não encontrado, verifique o código da Pauta ou se a Votação encontra-se em andamento!", exception.getMessage());
        verify(repository, times(1)).findFirstByPautaId(idPauta);
    }

    @Test
    void deveComputadorResultadoQuandoValido() {
        Long idPauta = 1L;

        Pauta pauta = new Pauta();
        pauta.setId(idPauta);

        Sessao sessao1 = new Sessao();
        sessao1.setFim(LocalDateTime.now().minusMinutes(10));
        sessao1.setId(1L);
        Voto voto1 = new Voto();
        voto1.setOpcaoVoto(OpcaoVoto.SIM);
        Voto voto2 = new Voto();
        voto2.setOpcaoVoto(OpcaoVoto.NAO);
        sessao1.setVotos(List.of(voto1, voto2));

        Sessao sessao2 = new Sessao();
        sessao2.setId(2L);
        sessao2.setFim(LocalDateTime.now().minusMinutes(10));
        Voto voto3 = new Voto();
        voto3.setOpcaoVoto(OpcaoVoto.SIM);
        Voto voto4 = new Voto();
        voto4.setOpcaoVoto(OpcaoVoto.SIM);
        sessao2.setVotos(List.of(voto3, voto4));

        List<Sessao> sessoes = List.of(sessao1, sessao2);

        when(pautaService.existsById(idPauta)).thenReturn(true);
        when(sessaoService.findByPautaId(idPauta)).thenReturn(sessoes);
        when(sessaoService.isSessaoAtiva(anyLong())).thenReturn(false);

        when(repository.save(any(Resultado.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Resultado resultado = resultadoService.computeResultado(idPauta);

        assertNotNull(resultado);
        assertEquals(idPauta, resultado.getPauta().getId());
        assertEquals(4L, resultado.getTotalVotos());
        assertEquals(3L, resultado.getTotalSim());
        assertEquals(1L, resultado.getTotalNao());

        verify(repository, times(1)).save(argThat(savedResultado ->
                savedResultado.getTotalVotos().equals(4L) &&
                        savedResultado.getTotalSim().equals(3L) &&
                        savedResultado.getTotalNao().equals(1L) &&
                        savedResultado.getPauta().getId().equals(idPauta)
        ));
    }

    @Test
    void deveRetornarErroAoTentarCalcularResultadoDePautaInexistente() {
        Long idPauta = 1L;

        when(pautaService.existsById(idPauta)).thenReturn(false);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            resultadoService.computeResultado(idPauta);
        });

        assertEquals("Nenhuma pauta encontrada com este ID!", exception.getMessage());
        verify(repository, times(0)).save(any(Resultado.class));
    }

    @Test
    void deveRetornarErroAoCalcularResultadoDeVotacaoComSessaoAtiva() {
        Long idPauta = 1L;

        Pauta pauta = new Pauta();
        pauta.setId(idPauta);

        Sessao sessao = new Sessao();
        sessao.setId(1L);
        sessao.setFim(LocalDateTime.now().plusMinutes(30));
        Voto voto = new Voto();
        voto.setOpcaoVoto(OpcaoVoto.SIM);
        sessao.setVotos(List.of(voto));

        List<Sessao> sessoes = List.of(sessao);

        when(pautaService.existsById(idPauta)).thenReturn(true);
        when(sessaoService.findByPautaId(idPauta)).thenReturn(sessoes);
        when(sessaoService.isSessaoAtiva(sessao.getId())).thenReturn(true);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            resultadoService.computeResultado(idPauta);
        });

        assertEquals("A Votação ainda está ativa, espere terminar até a última sessão!", exception.getMessage());
        verify(repository, times(0)).save(any(Resultado.class));
    }
}