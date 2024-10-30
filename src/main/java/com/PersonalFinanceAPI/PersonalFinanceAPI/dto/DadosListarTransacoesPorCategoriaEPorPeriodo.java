package com.PersonalFinanceAPI.PersonalFinanceAPI.dto;

import java.time.LocalDateTime;

public record DadosListarTransacoesPorCategoriaEPorPeriodo(String categoriaNome , LocalDateTime dataInicio, LocalDateTime dataFim) {
}
