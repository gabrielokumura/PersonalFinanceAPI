package com.PersonalFinanceAPI.PersonalFinanceAPI.controller;

import com.PersonalFinanceAPI.PersonalFinanceAPI.dto.DadosAtualizaCategoria;
import com.PersonalFinanceAPI.PersonalFinanceAPI.dto.DadosCadastroCategoria;
import com.PersonalFinanceAPI.PersonalFinanceAPI.dto.DadosListagemCategoria;
import com.PersonalFinanceAPI.PersonalFinanceAPI.dto.MetaFinanceiraDTO;
import com.PersonalFinanceAPI.PersonalFinanceAPI.model.Categoria;
import com.PersonalFinanceAPI.PersonalFinanceAPI.model.MetaFinanceira;
import com.PersonalFinanceAPI.PersonalFinanceAPI.model.Usuario;
import com.PersonalFinanceAPI.PersonalFinanceAPI.service.CategoriaService;
import com.PersonalFinanceAPI.PersonalFinanceAPI.service.MetaFinanceiraService;
import com.PersonalFinanceAPI.PersonalFinanceAPI.service.repository.CategoriaRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public class MetaFinanceiraController {

    @Autowired
    private MetaFinanceiraService metaFinanceiraService;
    @Autowired
    private CategoriaRepository repository;

    @PostMapping
    public ResponseEntity<MetaFinanceiraDTO> cadastrarCategoria(@RequestBody @Valid MetaFinanceiraDTO dados, @AuthenticationPrincipal UserDetails usuarioLogado) {

        MetaFinanceiraDTO MetaFinanceira = metaFinanceiraService.cadastrarMetaFinanceira(dados, ((Usuario) usuarioLogado).getId());

        return ResponseEntity.ok(MetaFinanceira);
    }

    @GetMapping
    public ResponseEntity<List<DadosListagemCategoria>> listar() {
        List<DadosListagemCategoria> categories = categoriaService.listarCategorias();
        return ResponseEntity.ok(categories);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Categoria> atualizar(@PathVariable Long id,
                                               @Valid @RequestBody DadosAtualizaCategoria dados){
        Categoria categoria = categoriaService.atualizarCategoria(id, dados);
        return ResponseEntity.ok(categoria);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<?> excluir(@PathVariable Long id) {
        Categoria categoria = categoriaService.excluir(id);
        return  ResponseEntity.ok(categoria);
    }
}
