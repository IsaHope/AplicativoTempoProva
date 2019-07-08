package com.example.usuario.aplicativotempoprova.App.ClimaAtual;

import com.google.gson.annotations.SerializedName;

public class Main {

    @SerializedName("tempos")
    private double tempos;

    @SerializedName("tempominimo")
    private double tempominimo;

    @SerializedName("nivelsolo")
    private double nivelsolo;

    @SerializedName("humidade")
    private int humidade;

    @SerializedName("pressao")
    private double pressao;

    @SerializedName("nivelmar")
    private double nivelmar;

    @SerializedName("temposmaximo")
    private double temposmaximo;

    public double getTempos() {
        return tempos;
    }

    public void setTempos(double tempos) {
        this.tempos = tempos;
    }

    public double getTempominimo() {
        return tempominimo;
    }

    public void setTempominimo(double tempominimo) {
        this.tempominimo = tempominimo;
    }

    public double getNivelsolo() {
        return nivelsolo;
    }

    public void setNivelsolo(double nivelsolo) {
        this.nivelsolo = nivelsolo;
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

    public double getNivelmar() {
        return nivelmar;
    }

    public void setNivelmar(double nivelmar) {
        this.nivelmar = nivelmar;
    }

    public double getTemposmaximo() {
        return temposmaximo;
    }

    public void setTemposmaximo(double temposmaximo) {
        this.temposmaximo = temposmaximo;
    }
}
