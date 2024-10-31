package com.PersonalFinanceAPI.PersonalFinanceAPI.dto;

import java.math.BigDecimal;

public record DadosListagemCategoria(Long id, String nome, String Descricao, BigDecimal Orcamento, BigDecimal OrcamentoRestante) {
}
