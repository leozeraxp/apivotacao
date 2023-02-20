package com.desafiovagasicredi.service;

import com.desafiovagasicredi.model.entity.Associado;

public interface AssociadoService {
    public Associado salvar(Associado associado, String confirmarSenha);

    public String login(String username, String password);
}
