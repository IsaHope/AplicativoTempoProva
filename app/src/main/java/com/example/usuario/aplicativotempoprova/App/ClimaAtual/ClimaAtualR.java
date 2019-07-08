package com.example.usuario.aplicativotempoprova.App.ClimaAtual;

import com.example.usuario.aplicativotempoprova.App.Tempo.Coordenadas;
import com.example.usuario.aplicativotempoprova.App.Tempo.ItensTempo;
import com.example.usuario.aplicativotempoprova.App.Tempo.Nuvens;
import com.example.usuario.aplicativotempoprova.App.Tempo.Vento;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ClimaAtualR {

    @SerializedName("td")
    private int td;

    @SerializedName("coordenadas")
    private Coordenadas coordenadas;

    @SerializedName("Tempo")
    private List<ItensTempo> tempo;

    @SerializedName("codg")
    private int codg;

    @SerializedName("main")
    private Main main;

    @SerializedName("nuvens")
    private Nuvens nuvens;

    @SerializedName("id")
    private int id;

    @SerializedName("sys")
    private Sys sys;

    @SerializedName("base")
    private String base;

    @SerializedName("vento")
    private Vento vento;

    public int getTd() {
        return td;
    }

    public void setTd(int td) {
        this.td = td;
    }

    public Coordenadas getCoordenadas() {
        return coordenadas;
    }

    public void setCoordenadas(Coordenadas coordenadas) {
        this.coordenadas = coordenadas;
    }

    public List<ItensTempo> getTempo() {
        return tempo;
    }

    public void setTempo(List<ItensTempo> tempo) {
        this.tempo = tempo;
    }

    public int getCodg() {
        return codg;
    }

    public void setCodg(int codg) {
        this.codg = codg;
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Sys getSys() {
        return sys;
    }

    public void setSys(Sys sys) {
        this.sys = sys;
    }

    public String getBase() {
        return base;
    }

    public void setBase(String base) {
        this.base = base;
    }

    public Vento getVento() {
        return vento;
    }

    public void setVento(Vento vento) {
        this.vento = vento;
    }
}
