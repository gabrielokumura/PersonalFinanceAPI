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

import java.util.List;

@Service
public class TransacaoService {

    @Autowired
    private TransacaoRepository transacaoRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    public Transacao cadastrarTransacao(DadosLancarTransacao dados, Long usuarioId) {

        System.out.println("entrou no cadastrar transação");
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        Categoria categoria = categoriaRepository.findById(dados.categoriaId())
                .orElseThrow(() -> new RuntimeException("Categoria não encontrado"));

        Transacao transacao = new Transacao(dados, categoria, usuario);

        return transacaoRepository.save(transacao);
    }

    public List<DadosListagemTransacao> listarTransacoes() {
        List<DadosListagemTransacao> transacaos = transacaoRepository.findAllByAtivoTrue();
        return transacaos;
    }

    public Transacao atualizarTransacao(Long id, DadosAtualizaTransacao transacaoAtualizada) {
        Transacao transacao = transacaoRepository.getReferenceById(id);
        {
            transacao.setCategoria(transacaoAtualizada.categoria());
            transacao.setDescricao(transacaoAtualizada.descricao());
            transacao.setValor(transacaoAtualizada.valor());
            transacao.setTipo(transacaoAtualizada.tipo());
            transacao.setData(transacaoAtualizada.data());
        }

        return transacao;
    }

    public Usuario atualizarUsuario(Long id, DadosAtualizaUsuario dados) {
        // Verifica se a transação existe
        Usuario usuario = usuarioRepository.getReferenceById(id);
        {
            usuario.setNome(dados.nome());
            usuario.setEmail(dados.email());
            // Salva a transação atualizada
            return usuarioRepository.save(usuario);
        }
    }
}
