package com.example.usuario.aplicativotempoprova.App.Tempo;

import com.google.gson.annotations.SerializedName;

public class ItensTempo {

    @SerializedName("icone")
    private String icone;

    @SerializedName("descricao")
    private String descricao;

    @SerializedName("main")
    private String main;

    @SerializedName("id")
    private int id;

    public String getIcone() {
        return icone;
    }

    public void setIcone(String icone) {
        this.icone = icone;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getMain() {
        return main;
    }

    public void setMain(String main) {
        this.main = main;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
