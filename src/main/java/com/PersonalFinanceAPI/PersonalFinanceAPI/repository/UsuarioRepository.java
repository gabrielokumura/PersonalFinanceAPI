package com.PersonalFinanceAPI.PersonalFinanceAPI.service.repository;
import com.PersonalFinanceAPI.PersonalFinanceAPI.dto.DadosListagemUsuario;
import com.PersonalFinanceAPI.PersonalFinanceAPI.dto.UsuarioDTO;
import com.PersonalFinanceAPI.PersonalFinanceAPI.model.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Page<DadosListagemUsuario> findByAtivoTrue();

    UserDetails findByEmail(String email);
    List<DadosListagemUsuario> findAllByAtivoTrue();

    Usuario list();
}