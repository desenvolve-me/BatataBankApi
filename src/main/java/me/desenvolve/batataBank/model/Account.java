package me.desenvolve.batataBank.model;

import me.desenvolve.batataBank.Exceptions.InvalidTransactionException;

import java.util.ArrayList;
import java.util.List;

public class Account {

    private Integer id;

    private Integer agencia;

    private Integer conta;

    private String cpf;

    private Double saldo = 0.0;

    private BillingAddress enderecoDeCobranca;

    private List<Transaction> transactions;

    public Account(Integer id, Integer agencia, Integer conta, String cpf, BillingAddress enderecoDeCobranca) {
        this.id = id;
        this.agencia = agencia;
        this.conta = conta;
        this.cpf = cpf;
        this.enderecoDeCobranca = enderecoDeCobranca;
        this.transactions = new ArrayList<>();
    }

    public Integer getId() {
        return id;
    }

    public Integer getAgencia() {
        return agencia;
    }

    public Integer getConta() {
        return conta;
    }

    public String getCpf() {
        return cpf;
    }

    public Double getSaldo() {
        return saldo;
    }

    public void realizaTrasacao(Transaction transaction) {

        if(transaction.getTipo() == TransactionType.CREDITO){
            this.saldo += transaction.getValor();
        }else{
            if(this.saldo < transaction.getValor()){
                throw new InvalidTransactionException("Saldo insuficiente");
            }

            this.saldo -= transaction.getValor();
        }

        this.transactions.add(transaction);
    }

    public BillingAddress getEnderecoDeCobranca() {
        return enderecoDeCobranca;
    }

    public void setEnderecoDeCobranca(BillingAddress enderecoDeCobranca) {
        this.enderecoDeCobranca = enderecoDeCobranca;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }
}
