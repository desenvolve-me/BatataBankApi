package me.desenvolve.batataBank.resources.dto;

import me.desenvolve.batataBank.model.TransactionType;

public class TransactionDTO {

    private String tipo;
    private Double valor;

    @Deprecated
    public TransactionDTO(){}

    public TransactionDTO(String tipo, Double valor) {
        this.tipo = tipo;
        this.valor = valor;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }
}
