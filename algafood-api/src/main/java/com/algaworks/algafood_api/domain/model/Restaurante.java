package com.algaworks.algafood_api.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Restaurante {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @Column(length = 30, nullable = false)
    private String nome;

    @Column(nullable = false)
    private BigDecimal taxaFrete;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Cozinha cozinha;

    @JsonIgnore
    @Embedded
    private Endereco endereco;

    @JsonIgnore
    @CreationTimestamp
    @Column(columnDefinition = "datetime")
    private LocalDateTime dataCadastro;

    @JsonIgnore
    @UpdateTimestamp
    @Column(columnDefinition = "datetime")
    private LocalDateTime dataAtualizacao;

    @JsonIgnore
    @ManyToMany
    @JoinTable(name = "restaurante_forma_pagamento",
            joinColumns = @JoinColumn(name = "restaurante_id"),
            inverseJoinColumns = @JoinColumn(name = "forma_pagamento_id"))
    private List<FormaPagamento> formasPagamento = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "restaurante")
    private List<Produto> produtos = new ArrayList<>();

}
