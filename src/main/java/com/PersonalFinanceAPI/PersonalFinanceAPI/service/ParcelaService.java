package com.PersonalFinanceAPI.PersonalFinanceAPI.service;

import com.PersonalFinanceAPI.PersonalFinanceAPI.model.Parcela;
import com.PersonalFinanceAPI.PersonalFinanceAPI.model.Transacao;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class ParcelaService {

    public List<Parcela> gerarParcelas(Transacao transacao) {
        List<Parcela> parcelas = new ArrayList<>();

        for (int i = 0; i < transacao.getQuantidadeParcelas(); i++) {
            Parcela parcela = new Parcela();
            parcela.setNumeroParcela(i + 1);
            parcela.setValorParcela(transacao.getValor().divide(BigDecimal.valueOf(transacao.getQuantidadeParcelas())));
            parcela.setDataVencimento(calcularDataVencimento(transacao.getDataVencimento(), transacao.getPeriodicidade(), i));
            parcela.setTransacao(transacao);
            parcelas.add(parcela);
        }

        return parcelas;
    }

    private LocalDate calcularDataVencimento(LocalDate dataInicial, String periodicidade, int numeroParcela) {
        switch (periodicidade.toUpperCase()) {
            case "MENSAL":
                return dataInicial.plusMonths(numeroParcela);
            case "SEMANAL":
                return dataInicial.plusWeeks(numeroParcela);
            // Adicione outras periodicidades, se necessário
            default:
                throw new IllegalArgumentException("Periodicidade inválida");
        }
    }
}
