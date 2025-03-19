package com.algaworks.algafood_api.api.assembler;

import com.algaworks.algafood_api.api.model.representationmodel.input.RestauranteInput;
import com.algaworks.algafood_api.domain.model.Cozinha;
import com.algaworks.algafood_api.domain.model.Restaurante;

/*
    Classe responsável por fazer transformações de model(input) para entidade.
    Assim podemos utilizar os métodos em outros controladores sem ficar repetindo código.
 */

public final class RestauranteInputDisassembler {

    public static Restaurante toDomainObject(RestauranteInput restauranteInput){
        Cozinha cozinha = new Cozinha();
        cozinha.setId(restauranteInput.getCozinha().getId());

        Restaurante restaurante = new Restaurante();
        restaurante.setNome(restauranteInput.getNome());
        restaurante.setTaxaFrete(restauranteInput.getTaxaFrete());
        restaurante.setCozinha(cozinha);

        return restaurante;
    }

}
