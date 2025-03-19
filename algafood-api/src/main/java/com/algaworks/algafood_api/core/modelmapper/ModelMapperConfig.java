package com.algaworks.algafood_api.core.modelmapper;

import com.algaworks.algafood_api.api.model.representationmodel.RestauranteModel;
import com.algaworks.algafood_api.domain.model.Restaurante;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper modelMapper(){
        var modelMapper = new ModelMapper();

        modelMapper.createTypeMap(Restaurante.class, RestauranteModel.class)
                .addMapping(Restaurante::getTaxaFrete, RestauranteModel::setPrecoFrete);

        return modelMapper;
    }

}
