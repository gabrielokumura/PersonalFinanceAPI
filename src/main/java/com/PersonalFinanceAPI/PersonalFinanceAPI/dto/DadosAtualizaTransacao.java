package com.PersonalFinanceAPI.PersonalFinanceAPI.dto;

import com.PersonalFinanceAPI.PersonalFinanceAPI.model.Categoria;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record DadosAtualizaTransacao(Categoria categoria, String descricao, BigDecimal valor, TipoTransacao tipo,
                                     LocalDateTime data) {
}
