package com.apivotacao.apivotacao.service.impl;

import com.apivotacao.apivotacao.model.entity.Sessao;
import com.apivotacao.apivotacao.repository.SessaoRepository;
import com.apivotacao.apivotacao.service.SessaoService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

import static java.util.Objects.*;

@Service
public class SessaoServiceImpl implements SessaoService {

    private SessaoRepository repository;

    public SessaoServiceImpl(SessaoRepository repository) {
        this.repository = repository;
    }

    @Override
    public Sessao create(Sessao sessao) {
        validar(sessao);

        LocalDateTime dataInicio = LocalDateTime.now();
        sessao.setInicio(dataInicio);

        if(isNull(sessao.getFim())){
            sessao.setFim(dataInicio.plusMinutes(1));
        }

        return repository.save(sessao);
    }

    @Override
    public boolean isSessaoAtiva(Long codigo) {
        Sessao sessao = repository.getReferenceById(codigo);

        return sessao.getFim().isAfter(LocalDateTime.now());
    }

    @Override
    public List<Sessao> findByPautaId(Long idPauta) {
        return repository.findByPautaId(idPauta);
    }

    private void validar(Sessao sessao) {
        if(isNull(sessao.getPauta())){
            throw new IllegalArgumentException("A sessão deve receber uma Pauta para ser iniciada!");
        }
    }
}
