package com.PersonalFinanceAPI.PersonalFinanceAPI.infra.security;

import com.PersonalFinanceAPI.PersonalFinanceAPI.model.Usuario;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.temporal.ValueRange;

@Service
public class TokenService {
    @Value("{api,security.token.secret}")
    private String secret;
    public String gerarToken(Usuario usuario){
        System.out.println("entrou no gerar token !");
        System.out.println(secret);
        try {
            var algorithm = Algorithm.HMAC256(secret);
            var token =JWT.create()
                    .withIssuer("API Personal Finance")
                    .withSubject(usuario.getUsername())
                    .withExpiresAt(dataExpiracao())
                    .sign(algorithm);
            System.out.println("token Criado: " + token);
            return token;
        } catch (JWTCreationException exception){
            throw new RuntimeException("erro ao gerar token", exception);
        }
    }

    public String getSubject(String tokenJWT){
        try {
            var algoritmo = Algorithm.HMAC256(secret);
            return JWT.require(algoritmo)
                    .withIssuer("API Personal Finance")
                    .build()
                    .verify(tokenJWT)
                    .getSubject();
        } catch (JWTVerificationException exception) {
            throw new RuntimeException("Token JWT inválido ou expirado!");
        }
    }

    private Instant dataExpiracao() {
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }
}
