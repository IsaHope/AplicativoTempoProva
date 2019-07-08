package com.example.usuario.aplicativotempoprova.App.TempoDias;

import com.example.usuario.aplicativotempoprova.App.Tempo.Coordenadas;
import com.google.gson.annotations.SerializedName;

public class Cidade {

    @SerializedName("pais")
    private String pais;

    @SerializedName("coordenadas")
    private Coordenadas coordenadas;

    @SerializedName("nome")
    private String nome;

    @SerializedName("id")
    private int id;

    @SerializedName("populacao")
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
