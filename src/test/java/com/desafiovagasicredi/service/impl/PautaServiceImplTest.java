package com.desafiovagasicredi.service.impl;

import com.desafiovagasicredi.exceptions.NotFoundException;
import com.desafiovagasicredi.exceptions.RegraNegocioException;
import com.desafiovagasicredi.exceptions.SenhaInvalidaException;
import com.desafiovagasicredi.model.entity.Associado;
import com.desafiovagasicredi.model.entity.Pauta;
import com.desafiovagasicredi.model.entity.enums.OpcoesVoto;
import com.desafiovagasicredi.model.repository.AssociadoRepository;
import com.desafiovagasicredi.model.repository.PautaRepository;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.data.jpa.repository.support.QuerydslJpaRepository;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.lang.reflect.Array;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@RunWith(SpringRunner.class)
@ActiveProfiles("test")
public class PautaServiceImplTest {
    @Autowired
    private PautaRepository pautaRepository;

    @Autowired
    private AssociadoRepository associadoRepository;

    @SpyBean
    private PautaServiceImpl pautaService;

    @Test
    public void salvarPautaComTemaValido() {
        Associado associadoSalvo = associadoRepository.save(Associado.builder().cpf("123456789").senha("123").build());

        Pauta pauta = Pauta.builder()
                .tema("Tema válido")
                .criador(Associado.builder().cpf("123456789").senha("123").build())
                .build();

        Pauta pautaSalva = pautaService.salvar(pauta);

        assertNotNull(pautaSalva);
        assertEquals("Tema válido", pautaSalva.getTema());
    }

    @Test
    public void salvarPautaComTemaInvalido() {
        Associado associadoSalvo = associadoRepository.save(Associado.builder().cpf("987654321").senha("123").build());

        Pauta pauta = Pauta.builder().tema(null).criador(associadoSalvo).build();

        List<String> msg = new ArrayList<>(Arrays.asList("Deve ser adicionado um tema para a Pauta!"));

        Throwable throwable = Assertions.catchThrowable(() -> pautaService.salvar(pauta));
        Assertions.assertThat(throwable).isInstanceOf(RegraNegocioException.class).hasMessage(String.join("\r\n", msg));
    }

    @Test
    public void deveRetornarErroAoTentarSalvarPautaComCriadorDeCpfNulo() {
        Associado associadoSalvo = associadoRepository.save(Associado.builder().cpf(null).build());

        Pauta pauta = Pauta.builder().tema(null).criador(associadoSalvo).build();

        List<String> msg = new ArrayList<>(Arrays.asList("O CPF não pode ser nulo!"));

        Throwable throwable = Assertions.catchThrowable(() -> pautaService.salvar(pauta));
        Assertions.assertThat(throwable).isInstanceOf(RegraNegocioException.class).hasMessage(String.join("\r\n", msg));
    }

    @Test
    public void deveRetornarErroAoTentarSalvarPautaComCriadorDeCpfEmBranco() {
        Associado associadoSalvo = associadoRepository.save(Associado.builder().cpf("").build());

        Pauta pauta = Pauta.builder().tema(null).criador(associadoSalvo).build();

        List<String> msg = new ArrayList<>(Arrays.asList("O CPF não pode ser nulo!"));

        Throwable throwable = Assertions.catchThrowable(() -> pautaService.salvar(pauta));
        Assertions.assertThat(throwable).isInstanceOf(RegraNegocioException.class).hasMessage(String.join("\r\n", msg));
    }

    @Test
    public void deveRetornarErroAoTentarSalvarPautaComCriadorDeCpfNaoCadastrado() {
        Associado associadoSalvo = associadoRepository.save(Associado.builder().cpf("6666444412").senha("123").build());

        Pauta pauta = Pauta.builder().criador(
                Associado.builder().cpf("111111111").build()
        ).build();

        List<String> msg = new ArrayList<>(Arrays.asList("Este CPF não foi encontrado em nossa base de dados!"));

        Throwable throwable = Assertions.catchThrowable(() -> pautaService.salvar(pauta));
        Assertions.assertThat(throwable).isInstanceOf(NotFoundException.class).hasMessage(String.join("\r\n", msg));
    }

    @Test
    public void deveRetornarErroAoAoTentarIniciarVotacaoComAssociadoDeCpfEmBranco() {
        Associado associado = Associado.builder().cpf("").build();
        Integer idPauta = null;
        Integer duracao = null;


        List<String> msg = new ArrayList<>(Arrays.asList("O CPF não pode ser nulo!"));

        Throwable throwable = Assertions.catchThrowable(() -> pautaService.iniciarVotacaoPauta(idPauta, associado, duracao));
        Assertions.assertThat(throwable).isInstanceOf(RegraNegocioException.class).hasMessage(String.join("\r\n", msg));
    }

    @Test
    public void deveRetornarErroAoTentarIniciarVotacaoComAssociadoDeCpfNaoCadastrado() {
        Associado associado = Associado.builder().cpf("00000000000").build();
        Integer idPauta = null;
        Integer duracao = null;

        List<String> msg = new ArrayList<>(Arrays.asList("Este CPF não foi encontrado em nossa base de dados!"));

        Throwable throwable = Assertions.catchThrowable(() -> pautaService.iniciarVotacaoPauta(idPauta, associado, duracao));
        Assertions.assertThat(throwable).isInstanceOf(NotFoundException.class).hasMessage(String.join("\r\n", msg));
    }


    @Test
    public void deveRetornarErroAoTentarIniciarVotacaoComPautaQueNaoExistente() {
        Associado associadoSalvo = associadoRepository.save(Associado.builder().cpf("01511922001").senha("123").build());

        Associado associado = Associado.builder().cpf("01511922001").senha("123").build();
        Integer idPauta = 0;
        Integer duracao = null;

        List<String> msg = new ArrayList<>(Arrays.asList("Não foi encontrado nenhuma pauta com este ID"));

        Throwable throwable = Assertions.catchThrowable(() -> pautaService.iniciarVotacaoPauta(idPauta, associado, duracao));
        Assertions.assertThat(throwable).isInstanceOf(NotFoundException.class).hasMessage(String.join("\r\n", msg));
    }

    @Test
    public void deveRetornarErroAoTentarIniciarVotacaoComCriadorDiferente() {
        Associado associadoSalvo = associadoRepository.save(Associado.builder().cpf("21010050001").senha("123").build());
        Associado associadoSalvoDiferente = associadoRepository.save(Associado.builder().cpf("77406818068").senha("123").build());
        Pauta pauta = pautaRepository.save(Pauta.builder().tema("tema válido").criador(associadoSalvo).build());

        Associado associado = Associado.builder().cpf("77406818068").senha("123").build();
        Integer idPauta = pauta.getId();
        Integer duracao = null;

        List<String> msg = new ArrayList<>(Arrays.asList("Apenas o criador da pauta pode iniciar a votação!"));

        Throwable throwable = Assertions.catchThrowable(() -> pautaService.iniciarVotacaoPauta(idPauta, associado, duracao));
        Assertions.assertThat(throwable).isInstanceOf(RegraNegocioException.class).hasMessage(String.join("\r\n", msg));
    }

    @Test
    public void deveRetornarErroAoTentarIniciarVotacaoComSessaoIniciada() {
        Associado associadoSalvo = associadoRepository.save(Associado.builder().cpf("92101072092").senha("123").build());
        Pauta pauta = pautaRepository.save(Pauta.builder().tema("tema válido").criador(associadoSalvo).inicioSessao(new Date()).build());

        Associado associado = Associado.builder().cpf("92101072092").senha("123").build();
        Integer idPauta = pauta.getId();
        Integer duracao = null;

        List<String> msg = new ArrayList<>(Arrays.asList("Esta sessão já foi iniciada!"));

        Throwable throwable = Assertions.catchThrowable(() -> pautaService.iniciarVotacaoPauta(idPauta, associado, duracao));
        Assertions.assertThat(throwable).isInstanceOf(RegraNegocioException.class).hasMessage(String.join("\r\n", msg));
    }


    @Test
    public void iniciarVotacaoPautaComSucessoSemDuracao() {
        Associado associadoSalvo = associadoRepository.save(Associado.builder().cpf("57779377059").senha("123").build());
        Pauta pauta = pautaRepository.save(Pauta.builder().tema("tema válido").criador(associadoSalvo).build());

        Associado associado = Associado.builder().cpf("57779377059").build();
        Integer idPauta = pauta.getId();
        Integer duracao = null;

        pautaService.iniciarVotacaoPauta(pauta.getId(), associado, null);

        pauta = pautaRepository.findById(pauta.getId()).get();

        assertNotNull(pauta.getInicioSessao());
        assertNotNull(pauta.getFimSessao());
    }

    @Test
    public void iniciarVotacaoPautaComSucessoComDuracao() {
        Associado associadoSalvo = associadoRepository.save(Associado.builder().cpf("58087166086").senha("123").build());
        Pauta pauta = pautaRepository.save(Pauta.builder().tema("tema válido").criador(associadoSalvo).build());

        Associado associado = Associado.builder().cpf("58087166086").senha("123").build();
        Integer idPauta = pauta.getId();
        Integer duracao = 600000;

        pautaService.iniciarVotacaoPauta(pauta.getId(), associado, duracao);

        pauta = pautaRepository.findById(pauta.getId()).get();

        assertNotNull(pauta.getInicioSessao());
        assertNotNull(pauta.getFimSessao());
        assertEquals((long) duracao, pauta.getFimSessao().getTime() - pauta.getInicioSessao().getTime());
    }

    @Test
    public void deveRetornarErroAoVotarEmPautaComIdInexistente(){
        Associado associado = null;
        Integer idPauta = 0;
        OpcoesVoto voto = null;

        List<String> msg = new ArrayList<>(Arrays.asList("Não foi encontrado nenhuma pauta com este ID"));

        Throwable throwable = Assertions.catchThrowable(() -> pautaService.votarPauta(associado,idPauta,voto));
        Assertions.assertThat(throwable).isInstanceOf(NotFoundException.class).hasMessage(String.join("\r\n", msg));
    }

    @Test
    public void deveRetornarErroAoVotarEmPautaComSessaoEncerrada(){
        Pauta pauta = pautaRepository.save(Pauta.builder().tema("tema válido").fimSessao(new Date(System.currentTimeMillis()-600000)).build());

        Associado associado = null;
        Integer idPauta = pauta.getId();
        OpcoesVoto voto = null;

        List<String> msg = new ArrayList<>(Arrays.asList("Esta sessão já foi encerrada!"));

        Throwable throwable = Assertions.catchThrowable(() -> pautaService.votarPauta(associado,idPauta,voto));
        Assertions.assertThat(throwable).isInstanceOf(RegraNegocioException.class).hasMessage(String.join("\r\n", msg));
    }


    //PAREI AQUI
    @Test
    public void deveRetornarErroAoVotarComAssociadoDeCpfEmBranco() {
        Pauta pauta = pautaRepository.save(Pauta.builder()
                .tema("tema válido")
                .inicioSessao(new Date(System.currentTimeMillis()))
                .fimSessao(new Date(System.currentTimeMillis()+600000))
                .build());

        Associado associado = Associado.builder().cpf("").build();
        Integer idPauta = pauta.getId();
        OpcoesVoto voto = null;


        List<String> msg = new ArrayList<>(Arrays.asList("O CPF não pode ser nulo!"));

        Throwable throwable = Assertions.catchThrowable(() -> pautaService.votarPauta(associado,idPauta,voto));
        Assertions.assertThat(throwable).isInstanceOf(RegraNegocioException.class).hasMessage(String.join("\r\n", msg));
    }

    @Test
    public void deveRetornarErroAoTentarVotarComAssociadoDeCpfNaoCadastrado() {
        Pauta pauta = pautaRepository.save(Pauta.builder()
                .tema("tema válido")
                .inicioSessao(new Date(System.currentTimeMillis()))
                .fimSessao(new Date(System.currentTimeMillis()+600000))
                .build());

        Associado associado = Associado.builder().cpf("000000000000").build();
        Integer idPauta = pauta.getId();
        OpcoesVoto voto = null;

        List<String> msg = new ArrayList<>(Arrays.asList("Este CPF não foi encontrado em nossa base de dados!"));

        Throwable throwable = Assertions.catchThrowable(() -> pautaService.votarPauta(associado,idPauta,voto));
        Assertions.assertThat(throwable).isInstanceOf(NotFoundException.class).hasMessage(String.join("\r\n", msg));
    }


    //Estou aqui
    @Test
    public void deveVotarCorretamenteAoSerOPrimeiroVoto(){
        Associado associadoSalvo = associadoRepository.save(Associado.builder().cpf("22400994021").senha("123").build());
        Pauta pauta = pautaRepository.save(Pauta.builder()
                .tema("tema válido")
                .inicioSessao(new Date(System.currentTimeMillis()))
                .fimSessao(new Date(System.currentTimeMillis()+600000))
                .build());

        Associado associado = Associado.builder().cpf("22400994021").build();
        Integer idPauta = pauta.getId();
        OpcoesVoto voto = OpcoesVoto.SIM;

        pautaService.votarPauta(associado,pauta.getId(),OpcoesVoto.SIM);
        pauta = pautaRepository.findById(pauta.getId()).get();

        List<OpcoesVoto> votos = new ArrayList<OpcoesVoto>();
        votos.add(OpcoesVoto.SIM);

        assertEquals(votos, pauta.getVotos());
    }

    @Test
    public void deveVotarCorretamenteAoSerOProximoVoto(){
        Associado associadoSalvo = associadoRepository.save(Associado.builder().cpf("42824062088").senha("123").build());

        List<OpcoesVoto> votos = new ArrayList<OpcoesVoto>(Arrays.asList(OpcoesVoto.SIM));
        Pauta pauta = pautaRepository.save(Pauta.builder()
                .tema("tema válido")
                        .votos(votos)
                .inicioSessao(new Date(System.currentTimeMillis()))
                .fimSessao(new Date(System.currentTimeMillis()+600000))
                .build());

        Associado associado = Associado.builder().cpf("42824062088").build();
        Integer idPauta = pauta.getId();
        OpcoesVoto voto = OpcoesVoto.SIM;

        pautaService.votarPauta(associado,pauta.getId(),OpcoesVoto.SIM);
        pauta = pautaRepository.findById(pauta.getId()).get();
        votos.add(OpcoesVoto.SIM);

        assertEquals(votos, pauta.getVotos());
        assertEquals(2, pauta.getVotos().size());
    }

    @Test
    public void deveVotarCorretamenteAoSerProximoAssociadoAVotar(){
        Associado associadoSalvo = associadoRepository.save(Associado.builder().cpf("70365138029").senha("123").build());

        List<Associado> associados = new ArrayList<Associado>(Arrays.asList(associadoSalvo));
        List<OpcoesVoto> votos = new ArrayList<OpcoesVoto>(Arrays.asList(OpcoesVoto.SIM));
        Pauta pauta = pautaRepository.save(Pauta.builder()
                .tema("tema válido")
                .votos(votos)
                .associados(associados)
                .inicioSessao(new Date(System.currentTimeMillis()))
                .fimSessao(new Date(System.currentTimeMillis()+600000))
                .build());

        Associado associado = associadoRepository.save(Associado.builder().cpf("89402874011").build());
        Integer idPauta = pauta.getId();
        OpcoesVoto voto = OpcoesVoto.SIM;

        pautaService.votarPauta(associado,pauta.getId(),OpcoesVoto.SIM);
        pauta = pautaRepository.findById(pauta.getId()).get();
        votos.add(OpcoesVoto.SIM);
        associados.add(associado);

        assertEquals(votos, pauta.getVotos());
        assertEquals(2, pauta.getVotos().size());
        assertEquals(associados, pauta.getAssociados());
    }

    @Test
    public void deveRetornarErroAoRetornarResultadoDeVotacaoComPautaDeIdInexistente(){
        Throwable throwable = Assertions.catchThrowable(() -> pautaService.retornarResultadoVotacaoPauta(0));
        Assertions.assertThat(throwable)
                .isInstanceOf(NotFoundException.class)
                .hasMessage("Não foi encontrado nenhuma pauta com este ID");
    }

    @Test
    public void deveRetornarErroAoRetornarResultadoDeVotacaoComPautaDeSessaoNaoFinalizada(){
        Pauta pauta = pautaRepository.save(Pauta.builder()
                .inicioSessao(new Date(System.currentTimeMillis()))
                .fimSessao(new Date(System.currentTimeMillis()+60000))
                .build());

        Throwable throwable = Assertions.catchThrowable(() -> pautaService.retornarResultadoVotacaoPauta(pauta.getId()));
        Assertions.assertThat(throwable)
                .isInstanceOf(RegraNegocioException.class)
                .hasMessage("Sessão ainda não foi finalizada!");
    }

    @Test
    public void deveRetornarQueASessaoNaoTeveVotosAoEstarVazio(){
        Pauta pauta = pautaRepository.save(Pauta.builder()
                .fimSessao(new Date(System.currentTimeMillis()))
                .build());


        Throwable throwable = Assertions.catchThrowable(() -> pautaService.retornarResultadoVotacaoPauta(pauta.getId()));
        Assertions.assertThat(throwable)
                .isInstanceOf(NotFoundException.class)
                .hasMessage("Esta pauta não teve nenhum voto durante sua sessão!");
    }

    @Test
    public void deveRetornarOResultadoDeVotacao(){
        List<OpcoesVoto> votos = new ArrayList<OpcoesVoto>(Arrays.asList(
           OpcoesVoto.SIM,OpcoesVoto.SIM, OpcoesVoto.SIM,OpcoesVoto.SIM,OpcoesVoto.SIM,OpcoesVoto.SIM,
           OpcoesVoto.NAO,OpcoesVoto.NAO,OpcoesVoto.NAO,OpcoesVoto.NAO
        ));

        Pauta pauta = pautaRepository.save(Pauta.builder()
                .fimSessao(new Date(System.currentTimeMillis()))
                .votos(votos)
                .build());

        Integer sim = Collections.frequency(votos, OpcoesVoto.SIM);
        Integer nao = Collections.frequency(votos, OpcoesVoto.NAO);
        Integer total = votos.size();

        Map<String, Integer> result = pautaService.retornarResultadoVotacaoPauta(pauta.getId());
        assertEquals(sim, result.get("sim"));
        assertEquals(nao,result.get("nao"));
        assertEquals(total,result.get("total"));
    }
}