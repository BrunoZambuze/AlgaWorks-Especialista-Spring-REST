package com.algaworks.algafood_api.api.assembler;

import com.algaworks.algafood_api.api.model.representationmodel.CozinhaModel;
import com.algaworks.algafood_api.api.model.representationmodel.RestauranteModel;
import com.algaworks.algafood_api.domain.model.Restaurante;

import java.util.List;
import java.util.stream.Collectors;

/*
    Classe responsável por fazer transformações de entidade para model.
    Assim podemos utilizar os métodos em outros controladores sem ficar repetindo código.
 */

public final class RestauranteModelAssembler {

    public static RestauranteModel toModel(Restaurante restaurante){

        CozinhaModel cozinhaModel = new CozinhaModel();
        cozinhaModel.setId(restaurante.getCozinha().getId());
        cozinhaModel.setNome(restaurante.getCozinha().getNome());

        RestauranteModel restauranteModel = new RestauranteModel();
        restauranteModel.setId(restaurante.getId());
        restauranteModel.setNome(restaurante.getNome());
        restauranteModel.setTaxaFrete(restaurante.getTaxaFrete());
        restauranteModel.setCozinha(cozinhaModel);

        return restauranteModel;

    }

    public static List<RestauranteModel> toCollectionModel(List<Restaurante> restaurantes){
        return restaurantes.stream()
                .map(RestauranteModelAssembler::toModel)
                .collect(Collectors.toList());
    }

}
