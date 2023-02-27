package com.desafiovagasicredi.api.controller;

import com.desafiovagasicredi.api.dto.AssociadoDto;
import com.desafiovagasicredi.api.dto.AuthResponseDTO;
import com.desafiovagasicredi.api.dto.LoginDto;
import com.desafiovagasicredi.exceptions.RegraNegocioException;
import com.desafiovagasicredi.model.entity.Associado;
import com.desafiovagasicredi.service.AssociadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/associados/")
public class AssociadoController {
    @Autowired
    private AssociadoService service;

    @PostMapping
    public ResponseEntity<Object> salvar(@RequestBody AssociadoDto dto){
            Associado associado = Associado.builder()
                                            .nome(dto.getNome())
                                            .cpf(dto.getCpf())
                                            .senha(dto.getSenha())
                                            .build();

            service.salvar(associado, dto.getConfirmarSenha());
            return new ResponseEntity<Object>(associado, HttpStatus.CREATED);
    }
}
