package com.PersonalFinanceAPI.PersonalFinanceAPI.service;

import com.PersonalFinanceAPI.PersonalFinanceAPI.dto.DadosCadastroCategoria;
import com.PersonalFinanceAPI.PersonalFinanceAPI.dto.DadosListagemCategoria;
import com.PersonalFinanceAPI.PersonalFinanceAPI.model.Categoria;
import com.PersonalFinanceAPI.PersonalFinanceAPI.model.Usuario;
import com.PersonalFinanceAPI.PersonalFinanceAPI.repository.CategoriaRepository;
import com.PersonalFinanceAPI.PersonalFinanceAPI.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class CategoriaService {
    @Autowired
    private CategoriaRepository repository;


    @Autowired
    private UsuarioRepository usuarioRepository;
    public Categoria cadastrarCategoria(DadosCadastroCategoria dados, Long usuarioId) {

        Usuario usuario = usuarioRepository.getReferenceById(usuarioId);

        Categoria categoria = new Categoria(dados, usuario);

        return repository.save(categoria);

    }

    public List<DadosListagemCategoria> listarCategorias() {
        List<DadosListagemCategoria> categorias = repository.findAllByAtivoTrue();
        return categorias;
    }
}
