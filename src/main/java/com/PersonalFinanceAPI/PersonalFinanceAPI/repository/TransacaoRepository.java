package com.PersonalFinanceAPI.PersonalFinanceAPI.repository;

import com.PersonalFinanceAPI.PersonalFinanceAPI.dto.DadosListagemTransacao;
import com.PersonalFinanceAPI.PersonalFinanceAPI.model.Transacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TransacaoRepository extends JpaRepository<Transacao,Long> {
    List<DadosListagemTransacao> findAllByAtivoTrue();

//    @Query("SELECT new com.seuprojeto.DadosListagemTransacao(t.descricao, t.valor, t.data, c.nome) " +
//            "FROM Transacao t JOIN t.categoria c " +
//            "WHERE t.ativo = true")
//    List<DadosListagemTransacao> findAllTransacoes();
}
