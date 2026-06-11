package com.sigmul.model;

import java.sql.Timestamp;

public class MultaAplicada {

    private int id;
    private Policial policial;
    private Veiculo veiculo;
    private Motorista motorista;
    private Rodovia rodovia;
    private int km;
    private Timestamp dataHora;

    public MultaAplicada() {}

    public MultaAplicada(int id, Policial policial, Veiculo veiculo,
                         Motorista motorista, Rodovia rodovia,
                         int km, Timestamp dataHora) {
        this.id = id;
        this.policial = policial;
        this.veiculo = veiculo;
        this.motorista = motorista;
        this.rodovia = rodovia;
        this.km = km;
        this.dataHora = dataHora;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public int getKm() {
        return km;
    }

    public void setKm(int km) {
        this.km = km;
    }

    public Timestamp getDataHora() {
        return dataHora;
    }

    public void setDataHora(Timestamp dataHora) {
        this.dataHora = dataHora;
    }
}
