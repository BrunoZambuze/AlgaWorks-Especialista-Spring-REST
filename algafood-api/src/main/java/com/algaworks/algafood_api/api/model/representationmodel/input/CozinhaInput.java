package com.algaworks.algafood_api.api.model.representationmodel.input;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class CozinhaInput {

    @NotBlank
    private String nome;

}
