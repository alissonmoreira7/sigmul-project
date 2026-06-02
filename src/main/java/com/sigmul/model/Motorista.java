package com.sigmul.model;

public class Motorista {
    private String cnh;
    private String cpf;
    private String nome;
    private int pontoAcumulado;

    public Motorista(){}

    public Motorista(String cnh, String cpf, String nome, int pontoAcumulado){
        this.cnh = cnh;
        this.cpf = cpf;
        this.nome = nome;
        this.pontoAcumulado = pontoAcumulado;
    }

    public String getCnh() {
        return cnh;
    }
    public String getCpf() {
        return cpf;
    }
    public String getNome() {
        return nome;
    }
    public int getPontoAcumulado() {
        return pontoAcumulado;
    }

    public void setCnh(String cnh) {
        this.cnh = cnh;
    }
    public void setCpf(String cpf) {
        this.cpf = cpf;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    public void setPontoAcumulado(int pontoAcumulado) {
        this.pontoAcumulado = pontoAcumulado;
    }
}
