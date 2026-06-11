package com.sigmul.model;

public class Veiculo {
    private String placa;
    private String marca;
    private String modelo;
    private int anoDeFabricacao;
    private Motorista motorista;

    public Veiculo() {}

    public Veiculo(String placa, String marca, String modelo, int anoDeFabricacao, Motorista motorista) {
        this.placa = placa;
        this.marca = marca;
        this.modelo = modelo;
        this.anoDeFabricacao = anoDeFabricacao;
        this.motorista = motorista;
    }

    public String getPlaca() { return placa; }
    public void setPlaca(String placa) { this.placa = placa; }

    public String getMarca() { return marca; }
    public void setMarca(String marca) { this.marca = marca; }

    public String getModelo() { return modelo; }
    public void setModelo(String modelo) { this.modelo = modelo; }

    public int getAnoDeFabricacao() { return anoDeFabricacao; }
    public void setAnoDeFabricacao(int anoDeFabricacao) { this.anoDeFabricacao = anoDeFabricacao; }

    public Motorista getMotorista() { return motorista; }
    public void setMotorista(Motorista motorista) { this.motorista = motorista; }
}