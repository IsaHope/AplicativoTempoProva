package com.example.usuario.aplicativotempoprova.App.TempoDias;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class VariosTempoDiasResposta {

    @SerializedName("cidade")
    private Cidade cidade;

    @SerializedName("tnc")
    private int tnc;

    @SerializedName("codg")
    private String codg;

    @SerializedName("mensagem")
    private double mensagem;

    @SerializedName("lista")
    private List<ListaItem> lista;

    public Cidade getCidade() {
        return cidade;
    }

    public void setCidade(Cidade cidade) {
        this.cidade = cidade;
    }

    public int getTnc() {
        return tnc;
    }

    public void setTnc(int tnc) {
        this.tnc = tnc;
    }

    public String getCodg() {
        return codg;
    }

    public void setCodg(String codg) {
        this.codg = codg;
    }

    public double getMensagem() {
        return mensagem;
    }

    public void setMensagem(double mensagem) {
        this.mensagem = mensagem;
    }

    public List<ListaItem> getLista() {
        return lista;
    }

    public void setLista(List<ListaItem> lista) {
        this.lista = lista;
    }
}
