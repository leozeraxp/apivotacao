package com.desafiovagasicredi.api.dto;

import com.desafiovagasicredi.model.entity.enums.OpcoesVoto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VotarDto {

    private OpcoesVoto voto;
}
