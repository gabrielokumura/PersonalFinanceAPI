package com.PersonalFinanceAPI.PersonalFinanceAPI.service;

import com.PersonalFinanceAPI.PersonalFinanceAPI.model.Parcela;
import com.PersonalFinanceAPI.PersonalFinanceAPI.model.Transacao;
import com.PersonalFinanceAPI.PersonalFinanceAPI.service.repository.ParcelaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class ParcelaService {


    @Autowired
    private ParcelaRepository parcelaRepository;
    public List<Parcela> gerarParcelas(Transacao transacao) {
        List<Parcela> parcelas = new ArrayList<>();

        for (int i = 0; i < transacao.getQuantidadeParcelas(); i++) {
            Parcela parcela = new Parcela();
            parcela.setNumeroParcela(i + 1);

            parcela.setValorParcela(
                    transacao.getValor().divide(BigDecimal.valueOf(transacao.getQuantidadeParcelas()), 2, RoundingMode.DOWN)
            );
            BigDecimal totalCalculado = parcela.getValorParcela().multiply(BigDecimal.valueOf(transacao.getQuantidadeParcelas()));
            BigDecimal restante = transacao.getValor().subtract(totalCalculado);

            if (i == transacao.getQuantidadeParcelas() - 1) {
                parcela.setValorParcela(parcela.getValorParcela().add(restante));
            }

            parcela.setDataVencimento(
                        calcularDataVencimento(
                                transacao.getDataVencimento(),
                                transacao.getPeriodicidade(),
                                i
                        )
                );


            parcela.setTransacao(transacao);
            parcelas.add(parcela);
            parcelaRepository.save(parcela);
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
