package com.PersonalFinanceAPI.PersonalFinanceAPI.service.repository;

import com.PersonalFinanceAPI.PersonalFinanceAPI.dto.DadosListagemTransacao;
import com.PersonalFinanceAPI.PersonalFinanceAPI.model.Categoria;
import com.PersonalFinanceAPI.PersonalFinanceAPI.model.Transacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface TransacaoRepository extends JpaRepository<Transacao,Long> {
    List<DadosListagemTransacao> findAllByAtivoTrue();

    List<DadosListagemTransacao> findByCategoriaNomeAndDataBetween(String categoriaNome, LocalDateTime dataInicio, LocalDateTime dataFim);


//    @Query("SELECT new com.seuprojeto.DadosListagemTransacao(t.descricao, t.valor, t.data, c.nome) " +
//            "FROM Transacao t JOIN t.categoria c " +
//            "WHERE t.ativo = true")
//    List<DadosListagemTransacao> findAllTransacoes();
}
