package com.example.usuario.aplicativotempoprova.App.TempoDias;

import com.google.gson.annotations.SerializedName;

public class Tempo {

    @SerializedName("minimo")
    private double minimo;

    @SerializedName("maximo")
    private double maximo;

    @SerializedName("tarde")
    private double tarde;

    @SerializedName("noite")
    private double noite;

    @SerializedName("dia")
    private double dia;

    @SerializedName("manha")
    private double manha;

    public double getMinimo() {
        return minimo;
    }

    public void setMinimo(double minimo) {
        this.minimo = minimo;
    }

    public double getMaximo() {
        return maximo;
    }

    public void setMaximo(double maximo) {
        this.maximo = maximo;
    }

    public double getTarde() {
        return tarde;
    }

    public void setTarde(double tarde) {
        this.tarde = tarde;
    }

    public double getNoite() {
        return noite;
    }

    public void setNoite(double noite) {
        this.noite = noite;
    }

    public double getDia() {
        return dia;
    }

    public void setDia(double dia) {
        this.dia = dia;
    }

    public double getManha() {
        return manha;
    }

    public void setManha(double manha) {
        this.manha = manha;
    }
}
