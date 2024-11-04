package com.PersonalFinanceAPI.PersonalFinanceAPI.service;

import com.PersonalFinanceAPI.PersonalFinanceAPI.dto.DadosAtualizaUsuario;
import com.PersonalFinanceAPI.PersonalFinanceAPI.dto.DadosCadastroUsuario;
import com.PersonalFinanceAPI.PersonalFinanceAPI.dto.DadosListagemCategoria;
import com.PersonalFinanceAPI.PersonalFinanceAPI.dto.DadosListagemUsuario;
import com.PersonalFinanceAPI.PersonalFinanceAPI.model.Usuario;
import com.PersonalFinanceAPI.PersonalFinanceAPI.repository.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {
    private UsuarioRepository usuarioRepository;
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UsuarioService (UsuarioRepository usuarioRepository,PasswordEncoder passwordEncoder){
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Usuario cadastrarUsuario(DadosCadastroUsuario dados){
        String senhaCriptografada = passwordEncoder.encode(dados.senha());
        Usuario usuario = new Usuario(dados.nome(),dados.email(),senhaCriptografada);
        return usuarioRepository.save(usuario);
    }

    public Usuario excluirUsuario(Long id) {
            var usuario = usuarioRepository.getReferenceById(id);
            usuario.ativarDesativar();
            return usuario;
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

    public List<DadosListagemUsuario> listarUsuario() {
        List<DadosListagemUsuario> usuarios = usuarioRepository.findAllByAtivoTrue();
        return usuarios;
    }
}
