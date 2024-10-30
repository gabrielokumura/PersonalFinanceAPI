package com.PersonalFinanceAPI.PersonalFinanceAPI.dto;

import java.math.BigDecimal;

public record DadosListagemUsuario(Long id, String nome, String email, BigDecimal saldo) {

}
