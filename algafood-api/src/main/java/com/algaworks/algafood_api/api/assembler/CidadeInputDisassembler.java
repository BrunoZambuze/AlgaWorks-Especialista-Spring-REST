package com.algaworks.algafood_api.api.assembler;

import com.algaworks.algafood_api.api.model.representationmodel.input.CidadeInput;
import com.algaworks.algafood_api.domain.model.Cidade;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Component
public class CidadeInputDisassembler {

    @Autowired
    private ModelMapper modelMapper;

    @PersistenceContext
    private EntityManager entityManager;

    public Cidade toDomainObject(CidadeInput cidadeInput){
        return modelMapper.map(cidadeInput, Cidade.class);
    }

    public void toCopyDomain(CidadeInput cidadeInput, Cidade cidade){
        // Para evitar org.hibernate.HibernateException: identifier of an instance of
        // com.algaworks.algafood.domain.model.Estado was altered from 1 to 2
        entityManager.detach(cidade.getEstado());

        modelMapper.map(cidadeInput, cidade);
    }

}
