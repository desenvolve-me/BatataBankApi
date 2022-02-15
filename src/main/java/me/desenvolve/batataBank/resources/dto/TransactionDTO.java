package me.desenvolve.batataBank.resources.dto;

public class TransactionDTO {

    private TransactionType tipo;
    private Double valor;

    @Deprecated
    public TransactionDTO(){}

    public TransactionDTO(TransactionType tipo, Double valor) {
        this.tipo = tipo;
        this.valor = valor;
    }

    public TransactionType getTipo() {
        return tipo;
    }

    public void setTipo(TransactionType tipo) {
        this.tipo = tipo;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }
}
