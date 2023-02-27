package com.desafiovagasicredi.api.controller;

import com.desafiovagasicredi.api.dto.IniciarVotacaoDto;
import com.desafiovagasicredi.api.dto.PautaDto;
import com.desafiovagasicredi.api.dto.VotarDto;
import com.desafiovagasicredi.model.entity.Associado;
import com.desafiovagasicredi.model.entity.Pauta;
import com.desafiovagasicredi.service.PautaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/pautas/")
public class PautaController {

    @Autowired
    private PautaService service;

    @PostMapping
    public ResponseEntity<Pauta> salvar(@RequestBody PautaDto dto){
            Pauta pauta = Pauta.builder()
                    .tema(dto.getTema())
                    .criador(Associado.builder().cpf(dto.getCpf()).build())
                    .build();

            service.salvar(pauta);

            return new ResponseEntity<Pauta>(pauta, HttpStatus.CREATED);
    }

    @PatchMapping("/{id}/iniciar")
    public ResponseEntity iniciarVotacao(@PathVariable Integer id,
                                                 @RequestBody(required = false) IniciarVotacaoDto dto ) {
        Associado associado = Associado.builder()
                .cpf(dto.getCpf())
                .build();

        service.iniciarVotacaoPauta(id, associado, dto.getDuracao());

        return new ResponseEntity(HttpStatus.OK);
    }

    @PostMapping("/{id}/votar")
    public ResponseEntity votar(@PathVariable Integer id,
                                        @RequestBody VotarDto votar){

        Associado associado = Associado.builder()
                .cpf(votar.getCpf())
                .build();

            service.votarPauta(associado,id,votar.getVoto());
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("/{id}/resultado")
    public ResponseEntity<Map<String, Integer>> resultado(@PathVariable("id") Integer id){
            Map<String, Integer> resultado = service.retornarResultadoVotacaoPauta(id);
            return new ResponseEntity<Map<String, Integer>>(resultado, HttpStatus.OK);
    }
}
