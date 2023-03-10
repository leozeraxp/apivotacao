package com.desafiovagasicredi.service;

import com.desafiovagasicredi.model.entity.Associado;
import com.desafiovagasicredi.model.entity.Pauta;
import com.desafiovagasicredi.model.entity.enums.OpcoesVoto;

import java.util.Map;

public interface PautaService {

    public Pauta salvar(Pauta pauta);

    public void iniciarVotacaoPauta(Integer idPauta, Associado associado, Integer duracao);

    public void votarPauta(Associado associado, Integer idPauta, OpcoesVoto voto);

    public Map<String,Integer> retornarResultadoVotacaoPauta(Integer idPauta);
}
