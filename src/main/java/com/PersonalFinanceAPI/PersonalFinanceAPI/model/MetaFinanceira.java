package com.PersonalFinanceAPI.PersonalFinanceAPI.model;


import com.PersonalFinanceAPI.PersonalFinanceAPI.dto.MetaFinanceiraDTO;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "meta_financeira")
public class MetaFinanceira {

    public MetaFinanceira(MetaFinanceiraDTO dados, Usuario usuario){
        this.nome = dados.nome();
        this.prazo = dados.prazo();
        this.valorAlvo = dados.valorAlvo();
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String nome;

    @Column(nullable = false)
    private Double valorAlvo;
    private boolean ativo;

    @Column(nullable = false)
    LocalDate prazo;

    @Column
    private Double progressoAtual;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;
}