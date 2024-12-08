package com.apivotacao.apivotacao.model.entity;

import jakarta.persistence.*;

@Entity
public class Resultado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private Pauta pauta;

    private Long totalVotos;

    private Long totalSim;

    private Long totalNao;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Pauta getPauta() {
        return pauta;
    }

    public void setPauta(Pauta pauta) {
        this.pauta = pauta;
    }

    public Long getTotalVotos() {
        return totalVotos;
    }

    public void setTotalVotos(Long totalVotos) {
        this.totalVotos = totalVotos;
    }

    public Long getTotalSim() {
        return totalSim;
    }

    public void setTotalSim(Long totalSim) {
        this.totalSim = totalSim;
    }

    public Long getTotalNao() {
        return totalNao;
    }

    public void setTotalNao(Long totalNao) {
        this.totalNao = totalNao;
    }
}
