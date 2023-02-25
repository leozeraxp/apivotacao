package com.desafiovagasicredi.api.controller;

import com.desafiovagasicredi.api.dto.AssociadoDto;
import com.desafiovagasicredi.api.dto.IniciarVotacaoDto;
import com.desafiovagasicredi.api.dto.PautaDto;
import com.desafiovagasicredi.api.dto.VotarDto;
import com.desafiovagasicredi.exceptions.RegraNegocioException;
import com.desafiovagasicredi.model.entity.Associado;
import com.desafiovagasicredi.model.entity.Pauta;
import com.desafiovagasicredi.model.entity.enums.OpcoesVoto;
import com.desafiovagasicredi.service.PautaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/pautas/")
public class PautaController {

    @Autowired
    private PautaService service;

    @PostMapping
    public ResponseEntity<Object> salvar(@RequestBody PautaDto dto){
            Pauta pauta = Pauta.builder()
                    .tema(dto.getTema())
                    .criador(Associado.builder().cpf(dto.getCpf()).build())
                    .build();

            service.salvar(pauta);

            return new ResponseEntity<Object>(pauta, HttpStatus.CREATED);
    }

    @PatchMapping("/{id}/iniciar")
    public ResponseEntity<String> iniciarVotacao(@PathVariable Integer id,
                                                 @RequestBody(required = false) IniciarVotacaoDto dto ) {
        Associado associado = Associado.builder()
                .cpf(dto.getCpf())
                .build();

        service.iniciarVotacaoPauta(id, associado, dto.getDuracao());

        return new ResponseEntity<String>("Votação Iniciada", HttpStatus.OK);
    }

    @PostMapping("/{id}/votar")
    public ResponseEntity<String> votar(@PathVariable Integer id,
                                        @RequestBody VotarDto votar){

        Associado associado = Associado.builder()
                .cpf(votar.getCpf())
                .build();

            service.votarPauta(associado,id,votar.getVoto());
        return new ResponseEntity<String>("Votação computado!", HttpStatus.OK);
    }

    @GetMapping("/{id}/resultado")
    public ResponseEntity<String> resultado(@PathVariable("id") Integer id){
            String resultado = service.retornarResultadoVotacaoPauta(id);
            return new ResponseEntity<>(resultado, HttpStatus.OK);
    }
}
