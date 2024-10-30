package com.PersonalFinanceAPI.PersonalFinanceAPI.model;

import com.PersonalFinanceAPI.PersonalFinanceAPI.dto.DadosCadastroCategoria;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;


@Entity
@Table(name = "categoria")
@AllArgsConstructor
@Getter
@Setter
public class Categoria {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String nome;

    @Column(length = 255)
    private String descricao;

    private boolean ativo;

    @JoinColumn(name = "usuario_id")
    @ManyToOne(fetch = FetchType.EAGER)
    @JsonIgnore // Ignorar a serialização do campo usuário
    private Usuario usuario;

    public Categoria(){
    }

    public Categoria(DadosCadastroCategoria dados, Usuario usuario) {
        this.nome = dados.nome();
        this.descricao = dados.descricao();
        this.ativo = true;
        this.usuario = usuario;
    }
}
