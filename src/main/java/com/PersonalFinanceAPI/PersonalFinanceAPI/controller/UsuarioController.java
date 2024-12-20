package com.PersonalFinanceAPI.PersonalFinanceAPI.controller;

import com.PersonalFinanceAPI.PersonalFinanceAPI.dto.DadosAtualizaUsuario;
import com.PersonalFinanceAPI.PersonalFinanceAPI.dto.DadosCadastroUsuario;
import com.PersonalFinanceAPI.PersonalFinanceAPI.dto.DadosListagemUsuario;
import com.PersonalFinanceAPI.PersonalFinanceAPI.dto.UsuarioDTO;
import com.PersonalFinanceAPI.PersonalFinanceAPI.model.Usuario;
import com.PersonalFinanceAPI.PersonalFinanceAPI.service.repository.UsuarioRepository;
import com.PersonalFinanceAPI.PersonalFinanceAPI.service.UsuarioService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {

    @Autowired
    UsuarioRepository repository;

    @Autowired
    UsuarioService usuarioService;

    @GetMapping
    public ResponseEntity<List<DadosListagemUsuario>> listar(Pageable paginacao){
        List<DadosListagemUsuario> usuarios = usuarioService.listarUsuario();
        return ResponseEntity.ok(usuarios);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Usuario> buscarUsuario(@PathVariable Long id) {
        Usuario usuario = repository.getReferenceById(id);
        return ResponseEntity.ok(usuario);
    }

    @PostMapping
    public ResponseEntity<Usuario> cadastrarUsuario(@RequestBody @Valid DadosCadastroUsuario dados) {
        Usuario usuario = usuarioService.cadastrarUsuario(dados);
        return ResponseEntity.ok(usuario);
    }
    @PutMapping("/{id}")
    public ResponseEntity<UsuarioDTO> atualizarUsuario(
            @PathVariable Long id,
            @Valid @RequestBody DadosAtualizaUsuario usuarioAtualizado) throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {

        // Chame o serviço para atualizar a transação
        UsuarioDTO usuarioDTO = usuarioService.atualizarUsuario(id,usuarioAtualizado);

        if (usuarioDTO != null) {
            return ResponseEntity.ok(usuarioDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
        @DeleteMapping("/{id}")
        @Transactional
        public ResponseEntity<?> excluir(@PathVariable Long id) {

            Usuario usuario = usuarioService.excluirUsuario(id);
            return  ResponseEntity.ok(usuario);
        }
    }


