package com.apivotacao.apivotacao.service;

import com.apivotacao.apivotacao.model.entity.Pauta;

import java.util.Map;

public interface PautaService {

    Pauta create(Pauta pauta);

    boolean existsById(Long idPauta);
}
