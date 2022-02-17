package me.desenvolve.batataBank.model;

public class Transaction {

    private TransactionType tipo;
    private Double valor;

    public Transaction(TransactionType tipo, Double valor) {
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
