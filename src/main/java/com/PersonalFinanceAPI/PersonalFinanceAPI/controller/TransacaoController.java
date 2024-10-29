package com.PersonalFinanceAPI.PersonalFinanceAPI.controller;

import com.PersonalFinanceAPI.PersonalFinanceAPI.dto.*;
import com.PersonalFinanceAPI.PersonalFinanceAPI.model.Transacao;
import com.PersonalFinanceAPI.PersonalFinanceAPI.model.Usuario;
import com.PersonalFinanceAPI.PersonalFinanceAPI.service.TransacaoService;
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
    private TransacaoService transacaoService;

    @PostMapping
    public ResponseEntity<Transacao> lancarTransacao(@RequestBody @Valid DadosLancarTransacao dados, @AuthenticationPrincipal UserDetails usuarioLogado) {

        Transacao transacao = transacaoService.cadastrarTransacao(dados, ((Usuario) usuarioLogado).getId());

        return ResponseEntity.ok(transacao);
    }

    @GetMapping
    public ResponseEntity<List<DadosListagemTransacao>> listarTransacoes(){
        List<DadosListagemTransacao> transacoes = transacaoService.listarTransacoes();
        return ResponseEntity.ok(transacoes);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Transacao> atualizarTransacao(
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



}
