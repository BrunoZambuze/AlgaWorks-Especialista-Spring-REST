package com.algaworks.algafood_api.api.assembler;

import com.algaworks.algafood_api.api.model.representationmodel.EstadoModel;
import com.algaworks.algafood_api.domain.model.Estado;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class EstadoModelAssembler {

    @Autowired
    private ModelMapper modelMapper;

    public EstadoModel toModel(Estado estado){
        return modelMapper.map(estado, EstadoModel.class);
    }

    public List<EstadoModel> toCollectionModel(List<Estado> estados){
        return estados.stream()
                .map(this::toModel)
                .collect(Collectors.toList());
    }

}
