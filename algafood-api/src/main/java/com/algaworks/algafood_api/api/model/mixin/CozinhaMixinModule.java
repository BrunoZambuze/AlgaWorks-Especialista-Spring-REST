package com.algaworks.algafood_api.api.model.mixin;

import com.algaworks.algafood_api.domain.model.Restaurante;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

public class CozinhaMixinModule {

    @JsonIgnore
    @OneToMany(mappedBy = "cozinha")
    private List<Restaurante> restaurantes = new ArrayList<>();

}
