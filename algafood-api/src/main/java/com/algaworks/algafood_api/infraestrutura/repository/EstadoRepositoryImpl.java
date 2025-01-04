package com.algaworks.algafood_api.infraestrutura.repository;

import com.algaworks.algafood_api.domain.exception.EstadoNaoEncontradoException;
import com.algaworks.algafood_api.domain.model.Estado;
import com.algaworks.algafood_api.domain.repository.EstadoRepositoryQueries;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class EstadoRepositoryImpl implements EstadoRepositoryQueries {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Estado findByIdOrElseThrowException(Long estadoId) {
        Estado estado = entityManager.find(Estado.class, estadoId);
        if(estado == null){
            throw new EstadoNaoEncontradoException(estadoId);
        }
        return estado;
    }
}
