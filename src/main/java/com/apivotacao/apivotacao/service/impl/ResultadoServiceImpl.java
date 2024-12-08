package com.apivotacao.apivotacao.service.impl;

import com.apivotacao.apivotacao.model.entity.Pauta;
import com.apivotacao.apivotacao.model.entity.Resultado;
import com.apivotacao.apivotacao.model.entity.Sessao;
import com.apivotacao.apivotacao.model.entity.Voto;
import com.apivotacao.apivotacao.model.enuns.OpcaoVoto;
import com.apivotacao.apivotacao.repository.ResultadoRepository;
import com.apivotacao.apivotacao.service.PautaService;
import com.apivotacao.apivotacao.service.ResultadoService;
import com.apivotacao.apivotacao.service.SessaoService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ResultadoServiceImpl implements ResultadoService {

    private ResultadoRepository repository;
    private SessaoService sessaoService;

    private PautaService pautaService;

    public ResultadoServiceImpl(ResultadoRepository repository, SessaoService sessaoService, PautaService pautaService) {
        this.repository = repository;
        this.sessaoService = sessaoService;
        this.pautaService = pautaService;
    }

    @Override
    public Resultado getResultado(Long idPauta) {
        Optional<Resultado> firstByIdPauta = repository.findFirstByPautaId(idPauta);

        return firstByIdPauta
                .orElseThrow(()-> new RuntimeException("Resultado para Pauta não encontrado, verifique o código da Pauta ou se a Votação encontra-se em andamento!"));
    }

    @Override
    public Resultado computeResultado(Long idPauta) {
        validarPauta(idPauta);

        Resultado entity = new Resultado();
        Pauta pauta = new Pauta();
        pauta.setId(idPauta);

        entity.setPauta(pauta);

        List<Sessao> sessoesPauta = sessaoService.findByPautaId(idPauta);

        Long totalVotos = 0L;
        Long totalSim = 0L;
        Long totalNao = 0L;

        for(Sessao sessao : sessoesPauta){
            validarSessaoAtiva(sessao);

            List<Voto> votos = sessao.getVotos();

            totalVotos += votos.size();
            totalSim += votos.stream().filter(voto -> Objects.equals(voto.getOpcaoVoto(), OpcaoVoto.SIM)).collect(Collectors.toList()).size();
            totalNao += votos.stream().filter(voto -> Objects.equals(voto.getOpcaoVoto(), OpcaoVoto.NAO)).collect(Collectors.toList()).size();
        }

        entity.setTotalSim(totalSim);
        entity.setTotalNao(totalNao);
        entity.setTotalVotos(totalVotos);

        return repository.save(entity);
    }

    private void validarPauta(Long idPauta) {
        if(!pautaService.existsById(idPauta)){
            throw new IllegalArgumentException("Nenhuma pauta encontrada com este ID!");
        }
    }

    private static void validarSessaoAtiva(Sessao sessao) {
        if(sessao.getFim().isAfter(LocalDateTime.now())){
            throw new IllegalArgumentException("A Votação ainda está ativa, espere terminar até a última sessão!");
        }
    }
}
