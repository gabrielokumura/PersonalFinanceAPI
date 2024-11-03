package com.PersonalFinanceAPI.PersonalFinanceAPI.model;

import com.PersonalFinanceAPI.PersonalFinanceAPI.dto.DadosCadastroCategoria;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;


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

    private BigDecimal orcamento;

    private BigDecimal orcamentoRestante;
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
        this.orcamento = dados.orcamento();
        this.orcamentoRestante = dados.orcamento();
    }

    public BigDecimal getOrcamentoRestante() {
        return orcamentoRestante;
    }

    public void setOrcamentoRestante(BigDecimal orcamentoRestante) {
        this.orcamentoRestante = orcamentoRestante;
    }

    public String getNome() {
        return nome;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public BigDecimal getOrcamento() {
        return orcamento;
    }

    public void setOrcamento(BigDecimal orcamento) {
        this.orcamento = orcamento;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}
