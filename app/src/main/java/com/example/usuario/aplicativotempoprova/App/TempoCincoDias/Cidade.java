package com.example.usuario.aplicativotempoprova.App.TempoCincoDias;

import com.example.usuario.aplicativotempoprova.App.Tempo.Coordenadas;

public class Cidade {

    private String pais;
    private Coordenadas coordenadas;
    private String nome;
    private int id;
    private int populacao;

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public Coordenadas getCoordenadas() {
        return coordenadas;
    }

    public void setCoordenadas(Coordenadas coordenadas) {
        this.coordenadas = coordenadas;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPopulacao() {
        return populacao;
    }

    public void setPopulacao(int populacao) {
        this.populacao = populacao;
    }
}
