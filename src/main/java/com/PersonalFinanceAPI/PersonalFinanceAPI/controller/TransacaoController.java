package com.PersonalFinanceAPI.PersonalFinanceAPI.controller;

import com.PersonalFinanceAPI.PersonalFinanceAPI.dto.*;
import com.PersonalFinanceAPI.PersonalFinanceAPI.model.Parcela;
import com.PersonalFinanceAPI.PersonalFinanceAPI.model.Transacao;
import com.PersonalFinanceAPI.PersonalFinanceAPI.model.Usuario;
import com.PersonalFinanceAPI.PersonalFinanceAPI.repository.TransacaoRepository;
import com.PersonalFinanceAPI.PersonalFinanceAPI.service.ParcelaService;
import com.PersonalFinanceAPI.PersonalFinanceAPI.service.TransacaoService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/transacao")
public class TransacaoController {

    @Autowired
    private ParcelaService parcelaService;

    @Autowired
    private TransacaoService transacaoService;

    @Autowired
    TransacaoRepository repository;

    @PostMapping
    public ResponseEntity<Transacao> lancarTransacao(@RequestBody @Valid DadosLancarTransacao dados, @AuthenticationPrincipal UserDetails usuarioLogado) {

        Transacao transacao = transacaoService.cadastrarTransacao(dados);
        return ResponseEntity.ok(transacao);
    }

    @GetMapping
    public ResponseEntity<List<DadosListagemTransacao>> listarTransacoes(){
        List<DadosListagemTransacao> transacoes = transacaoService.listarTransacoes();
        return ResponseEntity.ok(transacoes);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Transacao> buscarTransacaoPorId(@PathVariable Long id){
        return ResponseEntity.ok(repository.getReferenceById(id));
    }
    @GetMapping("/filtro")
    public ResponseEntity<List<DadosListagemTransacao>> listarTransacoesPorCategoriaEPeriodo(@RequestBody DadosListarTransacoesPorCategoriaEPorPeriodo dados){
        List<DadosListagemTransacao> transacoes = transacaoService.listarTransacoesPorPeriodoECAtegoria(dados);
        return ResponseEntity.ok(transacoes);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Transacao> atualizar(
            @PathVariable Long id,
            @Valid @RequestBody DadosAtualizaTransacao transacaoAtualizada) {

        // Chame o serviço para atualizar a transação
        Transacao transacao = transacaoService.atualizarTransacao(id,transacaoAtualizada);

        if (transacao != null) {
            return ResponseEntity.ok(transacao);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Transacao> excluir(@PathVariable Long id) {

        Transacao transacao = transacaoService.excluir(id);
        return  ResponseEntity.ok(transacao);
    }



}
