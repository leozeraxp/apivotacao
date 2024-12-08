package com.apivotacao.apivotacao.service;

import com.apivotacao.apivotacao.model.entity.Sessao;

import java.util.List;

public interface SessaoService {

    Sessao create(Sessao sessao);

    boolean isSessaoAtiva(Long id);

    List<Sessao> findByPautaId(Long idPauta);
}
