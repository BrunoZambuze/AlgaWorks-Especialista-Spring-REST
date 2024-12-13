package com.algaworks.algafood_api.domain.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String senha;

    @Column(nullable = false, columnDefinition = "datetime")
    @CreationTimestamp
    private LocalDateTime dataCadastro;

    @ManyToMany
    @JoinTable(name = "usuario_grupo",
            inverseJoinColumns = @JoinColumn(name = "grupo_id"))
    private List<Grupo> grupos = new ArrayList<>();

}
