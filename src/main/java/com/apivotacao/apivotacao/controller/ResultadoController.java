package com.apivotacao.apivotacao.controller;

import com.apivotacao.apivotacao.model.entity.Resultado;
import com.apivotacao.apivotacao.service.ResultadoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/resultado")
public class ResultadoController {

    private ResultadoService service;

    @Autowired
    public ResultadoController(ResultadoService service) {
        this.service = service;
    }

    @GetMapping("/{idPauta}")
    public ResponseEntity<Resultado> getResultado(@PathVariable("idPauta") Long idPauta){
        return new ResponseEntity<>(service.getResultado(idPauta), HttpStatus.OK);
    }

    @PostMapping("/calcular/{idPauta}")
    public ResponseEntity<Resultado> computeResultado(@PathVariable("idPauta") Long idPauta){
        return new ResponseEntity<>(service.computeResultado(idPauta), HttpStatus.CREATED);
    }
}
