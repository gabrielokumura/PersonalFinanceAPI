package com.PersonalFinanceAPI.PersonalFinanceAPI.dto;

import java.time.LocalDate;

public record MetaFinanceiraDTO(String nome, LocalDate prazo, Double valorAlvo) {
}
