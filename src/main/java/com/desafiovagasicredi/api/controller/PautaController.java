package com.desafiovagasicredi.api.controller;

import com.desafiovagasicredi.api.dto.AssociadoDto;
import com.desafiovagasicredi.api.dto.IniciarVotacaoDto;
import com.desafiovagasicredi.api.dto.PautaDto;
import com.desafiovagasicredi.api.dto.VotarDto;
import com.desafiovagasicredi.exceptions.RegraNegocioException;
import com.desafiovagasicredi.model.entity.Associado;
import com.desafiovagasicredi.model.entity.Pauta;
import com.desafiovagasicredi.model.entity.enums.OpcoesVoto;
import com.desafiovagasicredi.security.JwtGenerator;
import com.desafiovagasicredi.service.PautaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/pauta/")
public class PautaController {

    @Autowired
    private PautaService service;

    @Autowired
    private JwtGenerator jwtGenerator;

    @PostMapping("salvar")
    public ResponseEntity<Object> salvar(@RequestBody PautaDto dto, Authentication authentication){
        try{
            String cpf = authentication.getName();
            Pauta pauta = Pauta.builder()
                    .tema(dto.getTema())
                    .criador(Associado.builder().cpf(cpf).build())
                    .build();

            service.salvar(pauta);


            return new ResponseEntity<Object>(pauta, HttpStatus.CREATED);
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PatchMapping("/{id}/iniciar")
    public ResponseEntity<Object> iniciarVotacao(@PathVariable Integer id,
                                                 @RequestBody(required = false)IniciarVotacaoDto dto,
                                                 Authentication authentication){
        try {
            String cpf = authentication.getName();
            Associado associado = Associado.builder()
                    .cpf(cpf)
                    .build();

            service.iniciarVotacaoPauta(id,associado, dto.getDuracao());

            return ResponseEntity.ok().body("Votação iniciada!");
        }catch (RuntimeException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/{id}/votar")
    public ResponseEntity<Object> votar(@PathVariable Integer id,
                                        @RequestBody VotarDto votar,
                                        Authentication authentication){

        String cpf = authentication.getName();
        Associado associado = Associado.builder()
                .cpf(cpf)
                .build();

        try {
            service.votarPauta(associado,id,votar.getVoto());
            return ResponseEntity.ok().body("Voto computado!");

        }catch (RuntimeException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{id}/resultado")
    public ResponseEntity<Object> resultado(@PathVariable("id") Integer id){
        try {
            String resultado = service.retornarResultadoVotacaoPauta(id);
            return new ResponseEntity<>(resultado, HttpStatus.FOUND);

        }catch (RuntimeException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
