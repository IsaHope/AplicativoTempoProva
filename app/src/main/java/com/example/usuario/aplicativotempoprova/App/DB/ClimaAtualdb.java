package com.example.usuario.aplicativotempoprova.App.DB;


import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;

@Entity
public class ClimaAtualdb {

    @Id
    private long id;
    private double tempo;
    private int humidade;
    private String descricao;
    private String main;
    private int idTempo;
    private double direcaoVento;
    private double velocidadeVento;
    private long armazenarTempo;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public double getTempo() {
        return tempo;
    }

    public void setTempo(double tempo) {
        this.tempo = tempo;
    }

    public int getHumidade() {
        return humidade;
    }

    public void setHumidade(int humidade) {
        this.humidade = humidade;
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

    public int getIdTempo() {
        return idTempo;
    }

    public void setIdTempo(int idTempo) {
        this.idTempo = idTempo;
    }

    public double getDirecaoVento() {
        return direcaoVento;
    }

    public void setDirecaoVento(double direcaoVento) {
        this.direcaoVento = direcaoVento;
    }

    public double getVelocidadeVento() {
        return velocidadeVento;
    }

    public void setVelocidadeVento(double velocidadeVento) {
        this.velocidadeVento = velocidadeVento;
    }

    public long getArmazenarTempo() {
        return armazenarTempo;
    }

    public void setArmazenarTempo(long armazenarTempo) {
        this.armazenarTempo = armazenarTempo;
    }
}
