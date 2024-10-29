package com.PersonalFinanceAPI.PersonalFinanceAPI.repository;
import com.PersonalFinanceAPI.PersonalFinanceAPI.dto.DadosListagemCategoria;
import com.PersonalFinanceAPI.PersonalFinanceAPI.dto.DadosListagemUsuario;
import com.PersonalFinanceAPI.PersonalFinanceAPI.model.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Page<DadosListagemUsuario> findByAtivoTrue(Pageable paginacao);
    UserDetails findByEmail(String email);
    List<DadosListagemUsuario> findAllByAtivoTrue();
}