package com.PersonalFinanceAPI.PersonalFinanceAPI.dto;

import java.math.BigDecimal;

public record DadosAtualizaCategoria(String nome, String descricao,BigDecimal orcamento, BigDecimal orcamentoRestante) {
}
