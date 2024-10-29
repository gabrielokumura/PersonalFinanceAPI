package com.PersonalFinanceAPI.PersonalFinanceAPI.controller;

import com.PersonalFinanceAPI.PersonalFinanceAPI.dto.DadosAutenticacao;
import com.PersonalFinanceAPI.PersonalFinanceAPI.infra.security.DadosTokenJWT;
import com.PersonalFinanceAPI.PersonalFinanceAPI.infra.security.TokenService;
import com.PersonalFinanceAPI.PersonalFinanceAPI.model.Usuario;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class AutenticacaoController {

    @Autowired
    private TokenService tokenService;
    @Autowired
    private AuthenticationManager manager;
    @PostMapping
    public ResponseEntity efetuarLogin(@RequestBody @Valid DadosAutenticacao dados){
        System.out.println("Tentativa de login do usuario: " + dados.email() + ", senha: " + dados.senha());
        var authenticationToken = new UsernamePasswordAuthenticationToken(dados.email(),dados.senha());
        try{
            var authentication = manager.authenticate(authenticationToken);
            var tokenJWT = tokenService.gerarToken((Usuario) authentication.getPrincipal());
            System.out.println("token gerado com sucesso");
            return ResponseEntity.ok(new DadosTokenJWT(tokenJWT));

        } catch (Exception e){
            System.out.println("Erro durante a autenticação: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
}
