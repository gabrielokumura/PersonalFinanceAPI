package com.PersonalFinanceAPI.PersonalFinanceAPI.refl;

import com.PersonalFinanceAPI.PersonalFinanceAPI.dto.UsuarioDTO;
import com.PersonalFinanceAPI.PersonalFinanceAPI.model.Usuario;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;

class TransformatorTest {

    Usuario usuario = new Usuario("Teste","teste@teste.com","senha123");
    @Test
    public void deveriaTransformar() throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        Transformator transformator = new Transformator();
        UsuarioDTO usuarioDTO = transformator.transform(usuario);

        Assertions.assertInstanceOf(UsuarioDTO.class, usuarioDTO);
        Assertions.assertEquals(usuario.getNome(), usuarioDTO.getNome());
        Assertions.assertEquals(usuario.getEmail(), usuarioDTO.getEmail());
        Assertions.assertEquals(usuario.getSaldo(), usuarioDTO.getSaldo());

    }

}