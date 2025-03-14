package com.algaworks.algafood_api.api.model.mixin;

import com.algaworks.algafood_api.domain.model.Estado;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

public class CidadeMixinModule {

    @JsonIgnoreProperties(value = "nome", allowGetters = true)
    private Estado estado;

}
