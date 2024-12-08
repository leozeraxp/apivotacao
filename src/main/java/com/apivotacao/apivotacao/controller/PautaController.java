package com.apivotacao.apivotacao.controller;

import com.apivotacao.apivotacao.model.entity.Associado;
import com.apivotacao.apivotacao.model.entity.Pauta;
import com.apivotacao.apivotacao.service.AssociadoService;
import com.apivotacao.apivotacao.service.PautaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/pauta")
public class PautaController {

    private PautaService service;

    public PautaController(PautaService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Pauta> create(@RequestBody Pauta associado){
        return new ResponseEntity<>(service.create(associado), HttpStatus.CREATED);
    }
}
