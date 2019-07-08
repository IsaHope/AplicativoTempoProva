package com.example.usuario.aplicativotempoprova.App.Tempo;

import com.google.gson.annotations.SerializedName;

public class Nuvens {

    @SerializedName("all")
    private int all;

    public int getAll() {
        return all;
    }

    public void setAll(int all) {
        this.all = all;
    }
}
