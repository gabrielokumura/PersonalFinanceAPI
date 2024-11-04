package com.PersonalFinanceAPI.PersonalFinanceAPI.service;

import com.PersonalFinanceAPI.PersonalFinanceAPI.dto.*;
import com.PersonalFinanceAPI.PersonalFinanceAPI.model.Categoria;
import com.PersonalFinanceAPI.PersonalFinanceAPI.model.Parcela;
import com.PersonalFinanceAPI.PersonalFinanceAPI.model.Transacao;
import com.PersonalFinanceAPI.PersonalFinanceAPI.model.Usuario;
import com.PersonalFinanceAPI.PersonalFinanceAPI.repository.CategoriaRepository;
import com.PersonalFinanceAPI.PersonalFinanceAPI.repository.TransacaoRepository;
import com.PersonalFinanceAPI.PersonalFinanceAPI.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class TransacaoService {
    private TransacaoRepository transacaoRepository;
    private CategoriaRepository categoriaRepository;
    private UsuarioRepository usuarioRepository;
    private ParcelaService parcelaService;
    private EmailService emailService;

    @Autowired
    public TransacaoService(EmailService emailService, UsuarioRepository usuarioRepository,TransacaoRepository transacaoRepository,ParcelaService parcelaService,CategoriaRepository categoriaRepository) {
        this.emailService = emailService;
        this.usuarioRepository = usuarioRepository;
        this.transacaoRepository = transacaoRepository;
        this.parcelaService = parcelaService;
        this.categoriaRepository = categoriaRepository;
    }

    public Transacao cadastrarTransacao(DadosLancarTransacao dados) {

        Categoria categoria = categoriaRepository.findById(dados.categoriaId())
                .orElseThrow(() -> new RuntimeException("Categoria não encontrado"));

        Transacao transacao = new Transacao(dados, categoria);
        atualizarSaldoUsuario(transacao);

        Transacao transacao1 = transacaoRepository.save(transacao);
        parcelaService.gerarParcelas(transacao);

        return transacao1;
    }

    public void atualizarSaldoUsuario(Transacao transacao) {
        if (transacao.getTipo().equals(TipoTransacao.RECEITA)) {
            //Adiciona o valor da transação ao saldo do usuario
            transacao.getUsuario().setSaldo(transacao.getUsuario().getSaldo().add(transacao.getValor()));
        } else if (transacao.getTipo().equals(TipoTransacao.DESPESA)) {
            //Dispara e-mail caso a transação tenha estourado o orçamento limite
            if(transacao.getCategoria().getOrcamentoRestante() != null){
                if (transacao.getCategoria().getOrcamentoRestante().compareTo(BigDecimal.ZERO) < 0) {
                    emailService.enviarEmail(transacao.getUsuario().getEmail(), "Orçamento Atingido", "com sua ultima transação da categoria " + transacao.getCategoria().getNome() + " Você atingiu o orçamento predeterminado");
                }
            }
            //Subtrai o valor da transação do saldo do usuario
            transacao.getUsuario().setSaldo(transacao.getUsuario().getSaldo().subtract(transacao.getValor()));
        }
        usuarioRepository.save(transacao.getUsuario());
    }

    public List<DadosListagemTransacao> listarTransacoes() {
        List<DadosListagemTransacao> transacaos = transacaoRepository.findAllByAtivoTrue();
        return transacaos;
    }

    public List<DadosListagemTransacao> listarTransacoesPorPeriodoECAtegoria(DadosListarTransacoesPorCategoriaEPorPeriodo dados){
        List<DadosListagemTransacao> transacaos = transacaoRepository.findByCategoriaNomeAndDataBetween(dados.categoriaNome(),dados.dataInicio(),dados.dataFim());
        return transacaos;
    }

    public Transacao atualizarTransacao(Long id, DadosAtualizaTransacao transacaoAtualizada) {
        Transacao transacao = transacaoRepository.getReferenceById(id);
        {
            if(transacaoAtualizada.categoria() != null){
                transacao.setCategoria(transacaoAtualizada.categoria());
            }
            if(transacaoAtualizada.descricao() != null){
                transacao.setDescricao(transacaoAtualizada.descricao());
            }
            if(transacaoAtualizada.valor() != null){
                transacao.setValor(transacaoAtualizada.valor());
            }
            if(transacaoAtualizada.tipo() != null){
                transacao.setTipo(transacaoAtualizada.tipo());
            }
            if(transacaoAtualizada.data() != null){
                transacao.setData(transacaoAtualizada.data());
            }
        }
        atualizarSaldoUsuario(transacao);
        return transacaoRepository.save(transacao);
    }

    public Transacao excluir(Long id) {
        Transacao transacao = transacaoRepository.getReferenceById(id);
        transacao.setAtivo(!transacao.isAtivo());
        transacaoRepository.save(transacao);
        atualizarSaldoUsuario(transacao);
        return transacao;
    }


}
