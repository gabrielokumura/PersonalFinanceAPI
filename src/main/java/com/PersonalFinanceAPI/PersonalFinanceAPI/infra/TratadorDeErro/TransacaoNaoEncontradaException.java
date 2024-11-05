package com.PersonalFinanceAPI.PersonalFinanceAPI.infra.TratadorDeErro;

public class TransacaoNaoEncontradaException extends RuntimeException{
    public TransacaoNaoEncontradaException(Long id) {
        super("Transação com ID " + id + " não foi encontrada.");
    }
}
