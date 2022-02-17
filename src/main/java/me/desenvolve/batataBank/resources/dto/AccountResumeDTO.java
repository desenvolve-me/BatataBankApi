package me.desenvolve.batataBank.resources.dto;

import javax.validation.constraints.NotNull;

public class AccountResumeDTO {
    private Integer id;

    @NotNull(message = "Necessário informar agencia")
    private Integer agencia;

    @NotNull(message = "Necessário informar numero da conta")
    private Integer conta;

    @NotNull(message = "Necessário informar cpf")
    private String cpf;

    public AccountResumeDTO(Integer id, @NotNull(message = "Necessário informar agencia") Integer agencia, @NotNull(message = "Necessário informar numero da conta") Integer conta, @NotNull(message = "Necessário informar cpf") String cpf) {
        this.id = id;
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
}
