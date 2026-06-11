package com.sigmul.model;

import java.time.LocalDateTime;

public class MultaAplicada {

    private int idMulta;
    private Policial policial;
    private Veiculo veiculo;
    private Motorista motorista;
    private Rodovia rodovia;
    private int kmMulta;
    private LocalDateTime dataHoraMulta;

    public MultaAplicada() {}

    public MultaAplicada(int idMulta, Policial policial, Veiculo veiculo,
                          Motorista motorista, Rodovia rodovia,
                          int kmMulta, LocalDateTime dataHoraMulta) {
        this.idMulta = idMulta;
        this.policial = policial;
        this.veiculo = veiculo;
        this.motorista = motorista;
        this.rodovia = rodovia;
        this.kmMulta = kmMulta;
        this.dataHoraMulta = dataHoraMulta;
    }

    public int getIdMulta() {
        return idMulta;
    }

    public void setIdMulta(int idMulta) {
        this.idMulta = idMulta;
    }

    public Policial getPolicial() {
        return policial;
    }

    public void setPolicial(Policial policial) {
        this.policial = policial;
    }

    public Veiculo getVeiculo() {
        return veiculo;
    }

    public void setVeiculo(Veiculo veiculo) {
        this.veiculo = veiculo;
    }

    public Motorista getMotorista() {
        return motorista;
    }

    public void setMotorista(Motorista motorista) {
        this.motorista = motorista;
    }

    public Rodovia getRodovia() {
        return rodovia;
    }

    public void setRodovia(Rodovia rodovia) {
        this.rodovia = rodovia;
    }

    public int getKmMulta() {
        return kmMulta;
    }

    public void setKmMulta(int kmMulta) {
        this.kmMulta = kmMulta;
    }

    public LocalDateTime getDataHoraMulta() {
        return dataHoraMulta;
    }

    public void setDataHoraMulta(LocalDateTime dataHoraMulta) {
        this.dataHoraMulta = dataHoraMulta;
    }
}
