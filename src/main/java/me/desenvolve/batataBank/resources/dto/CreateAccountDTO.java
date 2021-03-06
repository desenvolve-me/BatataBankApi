package me.desenvolve.batataBank.resources.dto;

import javax.validation.constraints.NotNull;

public class CreateAccountDTO {

    @NotNull(message = "Necessário informar agencia")
    private Integer agencia;

    @NotNull(message = "Necessário informar numero da conta")
    private Integer conta;

    @NotNull(message = "Necessário informar cpf")
    private String cpf;

    @NotNull
    private BillingAddressDTO enderecoDeCobranca;


    @Deprecated
    CreateAccountDTO(){}

    public CreateAccountDTO(Integer agencia, Integer conta, String cpf, BillingAddressDTO enderecoDeCobranca) {
        this.agencia = agencia;
        this.conta = conta;
        this.cpf = cpf;
        this.enderecoDeCobranca = enderecoDeCobranca;
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

    public BillingAddressDTO getEnderecoDeCobranca() {
        return enderecoDeCobranca;
    }

    public void setEnderecoDeCobranca(BillingAddressDTO enderecoDeCobranca) {
        this.enderecoDeCobranca = enderecoDeCobranca;
    }
}
