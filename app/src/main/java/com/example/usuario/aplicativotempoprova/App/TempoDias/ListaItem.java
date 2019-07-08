package com.example.usuario.aplicativotempoprova.App.TempoDias;

import com.example.usuario.aplicativotempoprova.App.Tempo.ItensTempo;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ListaItem {

    @SerializedName("td")
    private int td;

    @SerializedName("tempotempo")
    private Tempo tempotempo;

    @SerializedName("direcao")
    private int direcao;

    @SerializedName("tempo")
    private List<ItensTempo> tempo;

    @SerializedName("humidade")
    private int humidade;

    @SerializedName("pressao")
    private double pressao;

    @SerializedName("nuvens")
    private int nuvens;

    @SerializedName("velocidade")
    private double velocidade;

    @SerializedName("chuva")
    private double chuva;

    public int getTd() {
        return td;
    }

    public void setTd(int td) {
        this.td = td;
    }

    public Tempo getTempotempo() {
        return tempotempo;
    }

    public void setTempotempo(Tempo tempotempo) {
        this.tempotempo = tempotempo;
    }

    public int getDirecao() {
        return direcao;
    }

    public void setDirecao(int direcao) {
        this.direcao = direcao;
    }

    public List<ItensTempo> getTempo() {
        return tempo;
    }

    public void setTempo(List<ItensTempo> tempo) {
        this.tempo = tempo;
    }

    public int getHumidade() {
        return humidade;
    }

    public void setHumidade(int humidade) {
        this.humidade = humidade;
    }

    public double getPressao() {
        return pressao;
    }

    public void setPressao(double pressao) {
        this.pressao = pressao;
    }

    public int getNuvens() {
        return nuvens;
    }

    public void setNuvens(int nuvens) {
        this.nuvens = nuvens;
    }

    public double getVelocidade() {
        return velocidade;
    }

    public void setVelocidade(double velocidade) {
        this.velocidade = velocidade;
    }

    public double getChuva() {
        return chuva;
    }

    public void setChuva(double chuva) {
        this.chuva = chuva;
    }
}
