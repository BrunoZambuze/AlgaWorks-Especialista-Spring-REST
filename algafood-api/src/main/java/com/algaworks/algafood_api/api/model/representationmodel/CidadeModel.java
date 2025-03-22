package com.algaworks.algafood_api.api.model.representationmodel;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CidadeModel {

    private Long id;
    private String nome;
    private EstadoModel estado;

}
