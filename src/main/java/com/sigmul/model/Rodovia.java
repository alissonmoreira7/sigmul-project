package com.sigmul.model;

public class Rodovia {
    private int id;
    private String codigoBR;
    private String estado;
    private int kilometros;

    public Rodovia(){}

    public Rodovia(int id, String codigoBR, String estado, int kilometros){
        this.id = id;
        this.codigoBR = codigoBR;
        this.estado = estado;
        this.kilometros = kilometros;
    }

    public int getId() {
        return id;
    }
    public String getCodigoBR() {
        return codigoBR;
    }
    public String getEstado() {
        return estado;
    }
    public int getKilometros() {
        return kilometros;
    }

    public void setId(int id) {
        this.id = id;
    }
    public void setCodigoBR(String codigoBR) {
        this.codigoBR = codigoBR;
    }
    public void setEstado(String estado) {
        this.estado = estado;
    }
    public void setKilometros(int kilometros) {
        this.kilometros = kilometros;
    }
}
