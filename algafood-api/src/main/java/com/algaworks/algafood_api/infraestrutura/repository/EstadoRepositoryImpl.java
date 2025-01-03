package com.algaworks.algafood_api.infraestrutura.repository;

import com.algaworks.algafood_api.domain.exception.EstadoNaoEncontradoException;
import com.algaworks.algafood_api.domain.model.Estado;
import com.algaworks.algafood_api.domain.repository.EstadoRepositoryQueries;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public class EstadoRepositoryImpl implements EstadoRepositoryQueries {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Estado findByEstadoOrElseThrowException(Long estadoId) {
        Estado estado = entityManager.find(Estado.class, estadoId);
        if(estado == null){
            throw new EstadoNaoEncontradoException("Não foi encontrado nenhum estado com id " + estadoId);
        }
        return estado;
    }
}
