package com.example.usuario.aplicativotempoprova.App.Tempo;

import com.google.gson.annotations.SerializedName;

public class Vento {

    @SerializedName("direcao")
    private double direcao;

    @SerializedName("velocidade")
    private double velocidade;

    public double getDirecao() {
        return direcao;
    }

    public void setDirecao(double direcao) {
        this.direcao = direcao;
    }

    public double getVelocidade() {
        return velocidade;
    }

    public void setVelocidade(double velocidade) {
        this.velocidade = velocidade;
    }
}
