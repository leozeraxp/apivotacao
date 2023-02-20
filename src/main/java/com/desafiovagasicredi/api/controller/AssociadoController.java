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
@RequestMapping("/api/associado/")
public class AssociadoController {
    @Autowired
    private AssociadoService service;

    @PostMapping
    public ResponseEntity<Object> salvar(@RequestBody AssociadoDto dto){

        try {
            Associado associado = Associado.builder()
                                            .nome(dto.getNome())
                                            .cpf(dto.getCpf())
                                            .senha(dto.getSenha())
                                            .build();

            service.salvar(associado, dto.getConfirmarSenha());
            return new ResponseEntity<Object>(associado, HttpStatus.CREATED);
        }catch (RegraNegocioException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("login")
    public ResponseEntity<Object> login(@RequestBody LoginDto loginDto){
        try{
            String token = service.login(loginDto.getUsername(), loginDto.getPassword());
            return new ResponseEntity<>(new AuthResponseDTO(token), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.FORBIDDEN);
        }

    }
}
