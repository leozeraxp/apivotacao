package com.apivotacao.apivotacao.controller;


import com.apivotacao.apivotacao.model.entity.Voto;
import com.apivotacao.apivotacao.service.VotoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/voto")
public class VotoController {

    private VotoService service;

    public VotoController(VotoService service) {
        this.service = service;
    }

    @PostMapping("/votar")
    public ResponseEntity<Voto> create(@RequestBody Voto voto){
        return new ResponseEntity<>(service.vote(voto), HttpStatus.CREATED);
    }
}
