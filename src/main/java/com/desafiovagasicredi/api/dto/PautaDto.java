package com.desafiovagasicredi.api.dto;

import com.desafiovagasicredi.model.entity.Associado;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PautaDto {

    private String tema;

    private String cpf;

}
