package com.PersonalFinanceAPI.PersonalFinanceAPI.dto;

import java.math.BigDecimal;

public class UsuarioDTO {
    private String nome;
    private String email;
    private BigDecimal saldo;
    public String getNome() {
        return nome;
    }
    public String getEmail() {
        return email;
    }
    public BigDecimal getSaldo() {
        return saldo;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setSaldo(BigDecimal saldo) {
        this.saldo = saldo;
    }
}
