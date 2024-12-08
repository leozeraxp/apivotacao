package com.apivotacao.apivotacao.service.impl;

import com.apivotacao.apivotacao.model.entity.Voto;
import com.apivotacao.apivotacao.repository.VotoRepository;
import com.apivotacao.apivotacao.service.SessaoService;
import com.apivotacao.apivotacao.service.VotoService;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class VotoServiceImpl implements VotoService {

    private VotoRepository repository;

    private SessaoService sessaoService;

    public VotoServiceImpl(VotoRepository repository, SessaoService sessaoService) {
        this.repository = repository;
        this.sessaoService = sessaoService;
    }

    @Override
    public Voto vote(Voto voto) {
        validar(voto);

        return repository.save(voto);
    }

    private void validar(Voto voto) {
        validarSessao(voto);

        if(Objects.isNull(voto.getAssociado())){
            throw new IllegalArgumentException("O voto deve receber um Associado válido!");
        }
    }

    private void validarSessao(Voto voto) {
        if(Objects.isNull(voto.getSessao()) || Objects.isNull(voto.getSessao().getId())){
            throw new IllegalArgumentException("O voto deve receber uma Sessão existente!");
        }

        if(!sessaoService.isSessaoAtiva(voto.getSessao().getId())){
            throw new IllegalArgumentException("O voto deve ser enviado para uma Sessão ativa!");
        }
    }
}
