package com.desafiovagasicredi.api.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class IniciarVotacaoDto {

    private Integer duracao;

    private String cpf;
}
