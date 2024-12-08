package com.apivotacao.apivotacao.controller;


import com.apivotacao.apivotacao.model.entity.Sessao;
import com.apivotacao.apivotacao.service.SessaoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/sessao")
public class SessaoController {

    private SessaoService service;

    public SessaoController(SessaoService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Sessao> create(@RequestBody Sessao sessao){
        return new ResponseEntity<>(service.create(sessao), HttpStatus.CREATED);
    }
}
