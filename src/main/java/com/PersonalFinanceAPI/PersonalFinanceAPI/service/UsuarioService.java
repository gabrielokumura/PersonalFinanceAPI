package com.PersonalFinanceAPI.PersonalFinanceAPI.service;

import com.PersonalFinanceAPI.PersonalFinanceAPI.dto.*;
import com.PersonalFinanceAPI.PersonalFinanceAPI.model.Usuario;
import com.PersonalFinanceAPI.PersonalFinanceAPI.refl.Transformator;
import com.PersonalFinanceAPI.PersonalFinanceAPI.service.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

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

    public UsuarioDTO atualizarUsuario(Long id, DadosAtualizaUsuario dados) throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        // Verifica se a transação existe
        Usuario usuario = usuarioRepository.getReferenceById(id);
        {
            usuario.setNome(dados.nome());
            usuario.setEmail(dados.email());
            // Salva a transação atualizada
            return new Transformator().transform(usuarioRepository.save(usuario));
        }
    }

    public UsuarioDTO list() throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        Usuario usuario = usuarioRepository.list();
        UsuarioDTO usuarioDTO = new Transformator().transform(usuario);
        return usuarioDTO;
    }

    public List<DadosListagemUsuario> listarUsuario() {
        return usuarioRepository.findAllByAtivoTrue();
    }
}
