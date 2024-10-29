package com.PersonalFinanceAPI.PersonalFinanceAPI.dto;

import com.PersonalFinanceAPI.PersonalFinanceAPI.model.Categoria;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record DadosListagemTransacao(Long id,
                                     String descricao,
                                     BigDecimal valor,
                                     LocalDateTime data,
                                     String categoriaNome) {
}
