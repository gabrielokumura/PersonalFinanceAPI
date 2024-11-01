package com.PersonalFinanceAPI.PersonalFinanceAPI.service;

import com.PersonalFinanceAPI.PersonalFinanceAPI.dto.*;
import com.PersonalFinanceAPI.PersonalFinanceAPI.model.Categoria;
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

    @Autowired
    private TransacaoRepository transacaoRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ParcelaService parcelaService;

    public Transacao cadastrarTransacao(DadosLancarTransacao dados, Long usuarioId) {



        System.out.println("entrou no cadastrar transação");
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        Categoria categoria = categoriaRepository.findById(dados.categoriaId())
                .orElseThrow(() -> new RuntimeException("Categoria não encontrado"));

        Transacao transacao = new Transacao(dados, categoria, usuario);
        atualizarSaldoUsuario(transacao.getUsuario(), transacao.getTipo(), transacao.getValor(), categoria);

        parcelaService.gerarParcelas(transacao);

        return transacaoRepository.save(transacao);
    }

    private void atualizarSaldoUsuario(Usuario usuario, TipoTransacao tipo, BigDecimal valor, Categoria categoria) {
        if (tipo.equals(TipoTransacao.RECEITA)) {
            categoria.setOrcamentoRestante(categoria.getOrcamentoRestante().subtract(valor));
            if(categoria.getOrcamentoRestante().compareTo(BigDecimal.ZERO) < 0) {
                System.out.println("Orcamento restante menor que zero");
                EmailService service = new EmailService();
                service.enviarEmail(usuario.getEmail(), "Orçamento Atingido", "com sua ultima transação da categoria " + categoria.getNome() + " Você atingiu o orçamento predeterminado");
            }
            usuario.setSaldo(usuario.getSaldo().add(valor));
        } else if (tipo.equals(TipoTransacao.DESPESA)) {
            usuario.setSaldo(usuario.getSaldo().subtract(valor));
        }
        usuarioRepository.save(usuario);
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
        atualizarSaldoUsuario(transacao.getUsuario(), transacao.getTipo(), transacao.getValor(), transacao.getCategoria());
        return transacaoRepository.save(transacao);
    }

    public Transacao excluir(Long id) {
        Transacao transacao = transacaoRepository.getReferenceById(id);
        transacao.setAtivo(!transacao.isAtivo());
        transacaoRepository.save(transacao);
        atualizarSaldoUsuario(transacao.getUsuario(), transacao.getTipo(), transacao.getValor(), transacao.getCategoria());
        return transacao;
    }


}
