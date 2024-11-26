package com.algaworks.algafood_api.domain.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.math.BigDecimal;

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

}
