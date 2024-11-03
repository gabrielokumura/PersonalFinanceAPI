package com.PersonalFinanceAPI.PersonalFinanceAPI.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public record DadosLancarTransacao(String descricao,
                                   @NotNull
                                   @Positive
                                   BigDecimal valor,
                                   LocalDateTime data,
                                   TipoTransacao tipo,
                                   Long categoriaId,
                                   String periodicidade,
                                   LocalDate dataVencimento,
                                   int quantidadeParcelas) {
}
