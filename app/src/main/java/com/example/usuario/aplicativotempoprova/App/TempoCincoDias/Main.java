package com.example.usuario.aplicativotempoprova.App.TempoCincoDias;

import com.google.gson.annotations.SerializedName;

public class Main {

    private double tempos;
    private double temposMinimo;
    private double nivelSolo;
    private double temposKf;
    private int humidade;
    private double pressao;
    private double nivelMar;

    @SerializedName("tempos_maximo")
    private double temposMaximo;

    public double getTempos() {
        return tempos;
    }

    public void setTempos(double tempos) {
        this.tempos = tempos;
    }

    public double getTemposMinimo() {
        return temposMinimo;
    }

    public void setTemposMinimo(double temposMinimo) {
        this.temposMinimo = temposMinimo;
    }

    public double getNivelSolo() {
        return nivelSolo;
    }

    public void setNivelSolo(double nivelSolo) {
        this.nivelSolo = nivelSolo;
    }

    public double getTemposKf() {
        return temposKf;
    }

    public void setTemposKf(double temposKf) {
        this.temposKf = temposKf;
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

    public double getNivelMar() {
        return nivelMar;
    }

    public void setNivelMar(double nivelMar) {
        this.nivelMar = nivelMar;
    }

    public double getTemposMaximo() {
        return temposMaximo;
    }

    public void setTemposMaximo(double temposMaximo) {
        this.temposMaximo = temposMaximo;
    }
}
