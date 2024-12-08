package com.apivotacao.apivotacao.model.entity;

import com.apivotacao.apivotacao.model.enuns.OpcaoVoto;
import jakarta.persistence.*;

import java.util.Date;

@Entity
public class Voto {

    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private Long id;

    private Date dataVoto;

    @ManyToOne
    private Associado associado;

    @ManyToOne
    private Sessao sessao;

    private OpcaoVoto opcaoVoto;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDataVoto() {
        return dataVoto;
    }

    public void setDataVoto(Date dataVoto) {
        this.dataVoto = dataVoto;
    }

    public Associado getAssociado() {
        return associado;
    }

    public void setAssociado(Associado associado) {
        this.associado = associado;
    }

    public Sessao getSessao() {
        return sessao;
    }

    public void setSessao(Sessao sessao) {
        this.sessao = sessao;
    }

    public OpcaoVoto getOpcaoVoto() {
        return opcaoVoto;
    }

    public void setOpcaoVoto(OpcaoVoto opcaoVoto) {
        this.opcaoVoto = opcaoVoto;
    }
}
