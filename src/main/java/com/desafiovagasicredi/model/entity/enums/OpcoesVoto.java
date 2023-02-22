package com.desafiovagasicredi.model.entity.enums;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


public enum OpcoesVoto {

    SIM,

    NAO;

    public String getOpcoesVoto(){
        return this.name();
    }
}
