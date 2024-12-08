package com.apivotacao.apivotacao.service.impl;

import com.apivotacao.apivotacao.model.entity.Pauta;
import com.apivotacao.apivotacao.repository.PautaRepository;
import com.apivotacao.apivotacao.service.PautaService;
import com.apivotacao.apivotacao.utils.Utils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class PautaServiceImpl implements PautaService {

    private PautaRepository repository;

    public PautaServiceImpl(PautaRepository repository) {
        this.repository = repository;
    }

    @Override
    public Pauta create(Pauta pauta) {
        validar(pauta);

        pauta.setCriacao(LocalDateTime.now());

        return repository.save(pauta);
    }

    private void validar(Pauta pauta) {
        if(Utils.isNotValid(pauta.getDescricao())){
            throw new IllegalArgumentException("A pauta deve possuir uma descrição!");
        }
    }

    @Override
    public boolean existsById(Long idPauta) {
        return repository.existsById(idPauta);
    }
}
