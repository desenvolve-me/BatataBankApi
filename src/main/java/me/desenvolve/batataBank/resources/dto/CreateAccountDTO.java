package me.desenvolve.batataBank.resources.dto;

import me.desenvolve.batataBank.Exceptions.InvalidTransactionException;

import javax.validation.constraints.NotNull;

public class CreateAccountDTO {

    private Integer id;

    @NotNull(message = "Necessário informar agencia")
    private Integer agencia;

    @NotNull(message = "Necessário informar numero da conta")
    private Integer conta;

    @NotNull(message = "Necessário informar cpf")
    private String cpf;

    private Double saldo = 0.0;

    @Deprecated
    CreateAccountDTO(){}

    public CreateAccountDTO(Integer agencia, Integer conta, String cpf) {
        this.agencia = agencia;
        this.conta = conta;
        this.cpf = cpf;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAgencia() {
        return agencia;
    }

    public void setAgencia(Integer agencia) {
        this.agencia = agencia;
    }

    public Integer getConta() {
        return conta;
    }

    public void setConta(Integer conta) {
        this.conta = conta;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public Double getSaldo() {
        return saldo;
    }

    public void realizaTrasacao(TransactionDTO transactionDTO) {

        if(transactionDTO.getTipo() == TransactionType.CREDITO){
            this.saldo += transactionDTO.getValor();
        }else{
            if(this.saldo < transactionDTO.getValor()){
                throw new InvalidTransactionException("Saldo insuficiente");
            }

            this.saldo -= transactionDTO.getValor();
        }


        this.saldo = saldo;
    }
}
