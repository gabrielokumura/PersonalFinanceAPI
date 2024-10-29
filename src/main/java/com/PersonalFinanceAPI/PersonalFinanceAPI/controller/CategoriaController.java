package com.PersonalFinanceAPI.PersonalFinanceAPI.controller;

import com.PersonalFinanceAPI.PersonalFinanceAPI.dto.DadosCadastroCategoria;
import com.PersonalFinanceAPI.PersonalFinanceAPI.dto.DadosListagemCategoria;
import com.PersonalFinanceAPI.PersonalFinanceAPI.model.Categoria;
import com.PersonalFinanceAPI.PersonalFinanceAPI.model.Usuario;
import com.PersonalFinanceAPI.PersonalFinanceAPI.repository.CategoriaRepository;
import com.PersonalFinanceAPI.PersonalFinanceAPI.service.CategoriaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categoria")
public class CategoriaController {

    @Autowired
    private CategoriaService categoriaService;
    @Autowired
    private CategoriaRepository repository;

    @PostMapping
    public ResponseEntity<Categoria> cadastrarCategoria(@RequestBody @Valid DadosCadastroCategoria dados, @AuthenticationPrincipal UserDetails usuarioLogado) {

        Categoria categoria = categoriaService.cadastrarCategoria(dados, ((Usuario) usuarioLogado).getId());

        return ResponseEntity.ok(categoria);
    }

    @GetMapping
    public ResponseEntity<List<DadosListagemCategoria>> listar() {
        List<DadosListagemCategoria> categories = categoriaService.listarCategorias();
        return ResponseEntity.ok(categories);
    }

}
