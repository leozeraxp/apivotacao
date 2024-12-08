package com.apivotacao.apivotacao.service;

import com.apivotacao.apivotacao.model.entity.Resultado;

public interface ResultadoService {

    Resultado getResultado(Long pauta);

    Resultado computeResultado(Long pauta);
}
