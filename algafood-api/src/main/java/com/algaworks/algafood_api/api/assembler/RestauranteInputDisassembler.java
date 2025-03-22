package com.algaworks.algafood_api.api.assembler;

import com.algaworks.algafood_api.api.model.representationmodel.input.RestauranteInput;
import com.algaworks.algafood_api.domain.model.Restaurante;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/*
    Classe responsável por fazer transformações de model(input) para entidade.
    Assim podemos utilizar os métodos em outros controladores sem ficar repetindo código.
 */

@Component
public class RestauranteInputDisassembler {

    @Autowired
    private ModelMapper modelMapper;

    @PersistenceContext
    private EntityManager entityManager;

    public Restaurante toDomainObject(RestauranteInput restauranteInput){
        return modelMapper.map(restauranteInput, Restaurante.class);
    }

    public void copyToDomainObject(RestauranteInput restauranteInput, Restaurante restaurante){
        // Para evitar org.hibernate.HibernateException: identifier of an instance of
        // com.algaworks.algafood.domain.model.Estado was altered from 1 to 2
        entityManager.detach(restaurante.getCozinha()); //Desanexar Cozinha do Restaurantew

        modelMapper.map(restauranteInput, restaurante);
    }

}
