package com.algaworks.algafood_api.api.assembler;

import com.algaworks.algafood_api.api.model.representationmodel.input.CozinhaInput;
import com.algaworks.algafood_api.domain.model.Cozinha;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;

@Component
public class CozinhaInputDisassembler {

    @Autowired
    private ModelMapper modelMapper;

    public Cozinha toObjectDomain(CozinhaInput cozinhaInput){
        return modelMapper.map(cozinhaInput, Cozinha.class);
    }

    public void toCopyDomain(CozinhaInput cozinhaInput, Cozinha cozinha){
        modelMapper.map(cozinhaInput, cozinha);
    }

}
