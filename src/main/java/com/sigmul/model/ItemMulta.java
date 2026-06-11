package com.sigmul.model;

public class ItemMulta {
    private Infracao infracao;
    private MultaAplicada multa;

    public ItemMulta() {}

    public ItemMulta(Infracao infracao, MultaAplicada multa) {
        this.infracao = infracao;
        this.multa = multa;
    }

    public Infracao getInfracao() { return infracao; }
    public void setInfracao(Infracao infracao) { this.infracao = infracao; }

    public MultaAplicada getMulta() { return multa; }
    public void setMulta(MultaAplicada multa) { this.multa = multa; }
}
