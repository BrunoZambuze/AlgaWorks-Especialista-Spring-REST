package com.algaworks.algafood_api.domain.model;

import com.algaworks.algafood_api.Groups;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.groups.ConvertGroup;
import javax.validation.groups.Default;

@Entity
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Cidade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @NotBlank
    @Column(nullable = false)
    private String nome;

    @ManyToOne
    @NotNull
    @Valid
    @ConvertGroup(from = Default.class, to = Groups.EstadoId.class)
    @JoinColumn(nullable = false)
    private Estado estado;

}
