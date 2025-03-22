package com.algaworks.algafood_api.api.model.representationmodel.input;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class EstadoIdInput {

    @NotNull
    private Long id;

}
