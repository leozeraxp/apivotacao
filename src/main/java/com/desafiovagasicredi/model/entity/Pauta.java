package com.desafiovagasicredi.model.entity;

import com.desafiovagasicredi.model.entity.enums.OpcoesVoto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.repository.cdi.Eager;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Pauta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String tema;

    @ManyToOne
    private Associado criador;

    private List<OpcoesVoto> votos = new ArrayList<OpcoesVoto>();

    @ManyToMany(fetch = FetchType.EAGER)
    private List<Associado> associados = new ArrayList<Associado>();

    private Date inicioSessao;

    private Date fimSessao;
}
