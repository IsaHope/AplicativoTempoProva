package com.example.usuario.aplicativotempoprova.App.TempoCincoDias;

import com.example.usuario.aplicativotempoprova.App.DB.HoraItemdb;

import java.util.List;

public class CincoDiasResposta {

    private Cidade cidade;
    private int tcn;
    private String codg;
    private double mensagem;
    private List<HoraItemdb> lista;

    public Cidade getCidade() {
        return cidade;
    }

    public void setCidade(Cidade cidade) {
        this.cidade = cidade;
    }

    public int getTcn() {
        return tcn;
    }

    public void setTcn(int tcn) {
        this.tcn = tcn;
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

    public List<HoraItemdb> getLista() {
        return lista;
    }

    public void setLista(List<HoraItemdb> lista) {
        this.lista = lista;
    }
}
