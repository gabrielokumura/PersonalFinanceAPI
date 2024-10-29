package com.PersonalFinanceAPI.PersonalFinanceAPI.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record DadosLancarTransacao(String descricao,
                                   BigDecimal valor,
                                   LocalDateTime data,
                                   TipoTransacao tipo,
                                   Long categoriaId) {
}
