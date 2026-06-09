package com.sigmul.model;

public class Infracao {
    private int id;
    private String nome;
    private String descricao;
    private double valorInfracao;
    private int pontosInfracao;

    public Infracao(){}

    public Infracao(int id, String nome, String descricao, double valorInfracao, int pontosInfracao){
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.valorInfracao = valorInfracao;
        this.pontosInfracao = pontosInfracao;
    }

    public int getId() {
        return id;
    }
    public String getNome() {
        return nome;
    }
    public String getDescricao() {
        return descricao;
    }
    public double getValorInfracao() {
        return valorInfracao;
    }
    public int getPontosInfracao() {
        return pontosInfracao;
    }

    public void setId(int id) {
        this.id = id;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
    public void setValorInfracao(double valorInfracao) {
        this.valorInfracao = valorInfracao;
    }
    public void setPontosInfracao(int pontosInfracao) {
        this.pontosInfracao = pontosInfracao;
    }
}
