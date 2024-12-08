package com.apivotacao.apivotacao.controller;

import com.apivotacao.apivotacao.model.entity.Associado;
import com.apivotacao.apivotacao.service.AssociadoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/associado")
public class AssociadoController {

    private AssociadoService service;

    public AssociadoController(AssociadoService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Associado> create(@RequestBody Associado associado){
        return new ResponseEntity<>(service.create(associado), HttpStatus.CREATED);
    }
}
