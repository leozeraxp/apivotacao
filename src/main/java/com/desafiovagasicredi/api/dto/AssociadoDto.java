package com.desafiovagasicredi.api.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class AssociadoDto {
    private String cpf;

    private String nome;

    private String senha;

    private String confirmarSenha;
}
