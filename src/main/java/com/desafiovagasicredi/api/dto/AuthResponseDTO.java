package com.desafiovagasicredi.api.dto;

import lombok.Data;

@Data
public class AuthResponseDTO {
    private String acessToken;
    private String tokenType = "Bearer ";

    public AuthResponseDTO(String acessToken) {
        this.acessToken = acessToken;
    }
}
