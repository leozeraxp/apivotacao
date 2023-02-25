package com.desafiovagasicredi.exceptions.handler;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Date;

@AllArgsConstructor
@Getter
public class ExceptionResponse {

    private int statusCode;
    private Date timestamp;
    private String message;

}
