package com.algaworks.algafood_api.core.jackson;

import com.algaworks.algafood_api.api.model.mixin.CidadeMixinModule;
import com.algaworks.algafood_api.api.model.mixin.CozinhaMixinModule;
import com.algaworks.algafood_api.api.model.mixin.RestauranteMixin;
import com.algaworks.algafood_api.domain.model.Cidade;
import com.algaworks.algafood_api.domain.model.Cozinha;
import com.algaworks.algafood_api.domain.model.Restaurante;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.springframework.stereotype.Component;

@Component
public class JacksonMixinModule extends SimpleModule {

    public JacksonMixinModule(){
        setMixInAnnotation(Restaurante.class, RestauranteMixin.class);
        setMixInAnnotation(Cidade.class, CidadeMixinModule.class);
        setMixInAnnotation(Cozinha.class, CozinhaMixinModule.class);
    }

}
