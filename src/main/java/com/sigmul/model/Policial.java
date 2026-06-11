package com.sigmul.model;

public class Policial {
    private int matricula;
    private String nome;
    private String cargo;

    public Policial() {}

    public Policial(int matricula, String nome, String cargo){
        this.matricula = matricula;
        this.nome = nome;
        this.cargo = cargo;
    }

    public int getMatricula() {
        return matricula;
    }
    public String getNome() {
        return nome;
    }
    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    public void setMatricula(int matricula) {
        this.matricula = matricula;
    }
}
