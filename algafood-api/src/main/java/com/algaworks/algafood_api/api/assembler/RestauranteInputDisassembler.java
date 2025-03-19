package com.algaworks.algafood_api.api.assembler;

import com.algaworks.algafood_api.api.model.representationmodel.input.RestauranteInput;
import com.algaworks.algafood_api.domain.model.Restaurante;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/*
    Classe responsável por fazer transformações de model(input) para entidade.
    Assim podemos utilizar os métodos em outros controladores sem ficar repetindo código.
 */

@Component
public class RestauranteInputDisassembler {

    @Autowired
    private ModelMapper modelMapper;

    public Restaurante toDomainObject(RestauranteInput restauranteInput){
        return modelMapper.map(restauranteInput, Restaurante.class);
    }

}
