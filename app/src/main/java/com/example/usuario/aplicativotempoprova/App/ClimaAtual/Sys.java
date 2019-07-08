package com.example.usuario.aplicativotempoprova.App.ClimaAtual;

import com.google.gson.annotations.SerializedName;

public class Sys {

    @SerializedName("pais")
    private String pais;

    @SerializedName("nascerdosol")
    private int nascerdosol;

    @SerializedName("pordosol")
    private int pordosol;

    @SerializedName("mensagem")
    private double mensagem;

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public int getNascerdosol() {
        return nascerdosol;
    }

    public void setNascerdosol(int nascerdosol) {
        this.nascerdosol = nascerdosol;
    }

    public int getPordosol() {
        return pordosol;
    }

    public void setPordosol(int pordosol) {
        this.pordosol = pordosol;
    }

    public double getMensagem() {
        return mensagem;
    }

    public void setMensagem(double mensagem) {
        this.mensagem = mensagem;
    }
}
