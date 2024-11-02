package com.PersonalFinanceAPI.PersonalFinanceAPI.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public record DadosLancarTransacao(String descricao,
                                   BigDecimal valor,
                                   LocalDateTime data,
                                   TipoTransacao tipo,
                                   Long categoriaId,
                                   String periodicidade,
                                   LocalDate dataVencimento,
                                   int quantidadeParcelas) {
}
