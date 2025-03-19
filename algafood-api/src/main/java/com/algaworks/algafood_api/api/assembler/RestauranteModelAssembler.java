package com.algaworks.algafood_api.api.assembler;

import com.algaworks.algafood_api.api.model.representationmodel.RestauranteModel;
import com.algaworks.algafood_api.domain.model.Restaurante;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/*
    Classe responsável por fazer transformações de entidade para model.
    Assim podemos utilizar os métodos em outros controladores sem ficar repetindo código.
 */

@Component
public class RestauranteModelAssembler {

    @Autowired
    private ModelMapper modelMapper;

    public RestauranteModel toModel(Restaurante restaurante){
        return modelMapper.map(restaurante, RestauranteModel.class);
    }

    public List<RestauranteModel> toCollectionModel(List<Restaurante> restaurantes){
        return restaurantes.stream()
                .map(this::toModel)
                .collect(Collectors.toList());
    }

}
