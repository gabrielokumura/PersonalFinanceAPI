package com.PersonalFinanceAPI.PersonalFinanceAPI.service;

import com.PersonalFinanceAPI.PersonalFinanceAPI.dto.DadosAtualizaCategoria;
import com.PersonalFinanceAPI.PersonalFinanceAPI.dto.DadosCadastroCategoria;
import com.PersonalFinanceAPI.PersonalFinanceAPI.dto.DadosListagemCategoria;
import com.PersonalFinanceAPI.PersonalFinanceAPI.model.Categoria;
import com.PersonalFinanceAPI.PersonalFinanceAPI.model.Usuario;
import com.PersonalFinanceAPI.PersonalFinanceAPI.service.repository.CategoriaRepository;
import com.PersonalFinanceAPI.PersonalFinanceAPI.service.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class CategoriaService {
    @Autowired
    private CategoriaRepository categoriaRepository;


    @Autowired
    private UsuarioRepository usuarioRepository;
    public Categoria cadastrarCategoria(DadosCadastroCategoria dados, Long usuarioId) {

        Usuario usuario = usuarioRepository.getReferenceById(usuarioId);

        Categoria categoria = new Categoria(dados, usuario);

        return categoriaRepository.save(categoria);

    }

    public List<DadosListagemCategoria> listarCategorias() {
        List<DadosListagemCategoria> categorias = categoriaRepository.findAllByAtivoTrue();
        return categorias;
    }

    public Categoria atualizarCategoria(Long id, DadosAtualizaCategoria dados) {
        Categoria categoria = categoriaRepository.getReferenceById(id);
        {
            if(dados.nome() != null){
                categoria.setNome(dados.nome());
            }
            if(dados.descricao() != null){
                categoria.setDescricao(dados.descricao());
            }
            if(dados.orcamento() != null){
                categoria.setOrcamento(dados.orcamento());
                categoria.setOrcamentoRestante(dados.orcamento());
            }
        }

        return categoriaRepository.save(categoria);
    }

    public Categoria excluir(Long id) {
        Categoria categoria = categoriaRepository.getReferenceById(id);
        categoria.setAtivo(!categoria.isAtivo());
        categoriaRepository.save(categoria);
        return categoria;
    }
}
