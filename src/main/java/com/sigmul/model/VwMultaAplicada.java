package com.sigmul.model;

import java.time.LocalDateTime;

public class VwMultaAplicada {
    private int idMulta;
    private LocalDateTime dataHora;
    private String motorista;
    private String cpfMoto;
    private String placa;
    private String veiculo;
    private String infracao;
    private double valor;
    private String rodovia;
    private String policial;

    public VwMultaAplicada() {}

    public int getIdMulta() { return idMulta; }
    public void setIdMulta(int idMulta) { this.idMulta = idMulta; }

    public LocalDateTime getDataHora() { return dataHora; }
    public void setDataHora(LocalDateTime dataHora) { this.dataHora = dataHora; }

    public String getMotorista() { return motorista; }
    public void setMotorista(String motorista) { this.motorista = motorista; }

    public String getCpfMoto() { return cpfMoto; }
    public void setCpfMoto(String cpfMoto) { this.cpfMoto = cpfMoto; }

    public String getPlaca() { return placa; }
    public void setPlaca(String placa) { this.placa = placa; }

    public String getVeiculo() { return veiculo; }
    public void setVeiculo(String veiculo) { this.veiculo = veiculo; }

    public String getInfracao() { return infracao; }
    public void setInfracao(String infracao) { this.infracao = infracao; }

    public double getValor() { return valor; }
    public void setValor(double valor) { this.valor = valor; }

    public String getRodovia() { return rodovia; }
    public void setRodovia(String rodovia) { this.rodovia = rodovia; }

    public String getPolicial() { return policial; }
    public void setPolicial(String policial) { this.policial = policial; }
}