package com.PersonalFinanceAPI.PersonalFinanceAPI.model;


import jakarta.persistence.*;

@Entity
@Table(name = "meta_financeira")
public class MetaFinanceira {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String nome;

    @Column(nullable = false)
    private Double valorAlvo;
    private boolean ativo;

    @Column(nullable = false)
    private String prazo; // Você pode usar java.util.Date ou java.time.LocalDate

    @Column
    private Double progressoAtual;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;
}