package com.example.usuario.aplicativotempoprova.App.TempoCincoDias;

import com.example.usuario.aplicativotempoprova.App.Tempo.ItensTempo;
import com.example.usuario.aplicativotempoprova.App.Tempo.Nuvens;
import com.example.usuario.aplicativotempoprova.App.Tempo.Vento;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ItemHora {

    private int td;
    private String tdTexto;
    private List<ItensTempo> tempo;
    private Main main;
    private Nuvens nuvens;

    @SerializedName("sys")
    private Sys sys;

    private Vento vento;
    private Chuva chuva;

    public int getTd() {
        return td;
    }

    public void setTd(int td) {
        this.td = td;
    }

    public String getTdTexto() {
        return tdTexto;
    }

    public void setTdTexto(String tdTexto) {
        this.tdTexto = tdTexto;
    }

    public List<ItensTempo> getTempo() {
        return tempo;
    }

    public void setTempo(List<ItensTempo> tempo) {
        this.tempo = tempo;
    }

    public Main getMain() {
        return main;
    }

    public void setMain(Main main) {
        this.main = main;
    }

    public Nuvens getNuvens() {
        return nuvens;
    }

    public void setNuvens(Nuvens nuvens) {
        this.nuvens = nuvens;
    }

    public Sys getSys() {
        return sys;
    }

    public void setSys(Sys sys) {
        this.sys = sys;
    }

    public Vento getVento() {
        return vento;
    }

    public void setVento(Vento vento) {
        this.vento = vento;
    }

    public Chuva getChuva() {
        return chuva;
    }

    public void setChuva(Chuva chuva) {
        this.chuva = chuva;
    }
}
