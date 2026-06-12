package com.sigmul.model;

public class ResumoMotorista {

    private final String nome;
    private final long totalMultas;
    private final double valorTotal;
    private final int pontosTotais;
    private final String infracaoMaisComum;

    public ResumoMotorista(String nome, long totalMultas, double valorTotal,
                           int pontosTotais, String infracaoMaisComum) {
        this.nome             = nome;
        this.totalMultas      = totalMultas;
        this.valorTotal       = valorTotal;
        this.pontosTotais     = pontosTotais;
        this.infracaoMaisComum = infracaoMaisComum;
    }

    public String getNome()               { return nome; }
    public long getTotalMultas()          { return totalMultas; }
    public double getValorTotal()         { return valorTotal; }
    public int getPontosTotais()          { return pontosTotais; }
    public String getInfracaoMaisComum()  { return infracaoMaisComum; }
}