package com.algaworks.algafood_api.infraestrutura.repository;

import com.algaworks.algafood_api.domain.exception.CidadeNaoEncontradaException;
import com.algaworks.algafood_api.domain.model.Cidade;
import com.algaworks.algafood_api.domain.repository.CidadeRepositoryQueries;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class CidadeRepositoryImpl implements CidadeRepositoryQueries {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Cidade findByIdOrElseThrowException(Long cidadeId){
        Cidade cidade = entityManager.find(Cidade.class, cidadeId);
        if(cidade == null){
            throw new CidadeNaoEncontradaException("Não foi encontrada nenhuma cidade com id " + cidadeId);
        }
        return cidade;
    }

}
