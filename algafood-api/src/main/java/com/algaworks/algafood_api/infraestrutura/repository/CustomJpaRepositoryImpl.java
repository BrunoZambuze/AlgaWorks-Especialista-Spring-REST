package com.algaworks.algafood_api.infraestrutura.repository;

import com.algaworks.algafood_api.domain.repository.CustomJpaRepository;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.Optional;

public class CustomJpaRepositoryImpl<T, ID> extends SimpleJpaRepository<T, ID> implements CustomJpaRepository<T, ID> {

    private EntityManager entityManager;

    public CustomJpaRepositoryImpl(JpaEntityInformation<T, ?> entityInformation, EntityManager entityManager) {
        super(entityInformation, entityManager);

        this.entityManager = entityManager;
    }

    @Override
    public Optional<T> buscarPrimeiro() {
        var jpql = "from " + getDomainClass().getName(); //getDomainClass(): Vai "pegar" a classe que estamos utilizando no SimpleJpaRepository
        TypedQuery<T> query = entityManager.createQuery(jpql, getDomainClass()).setMaxResults(1);
        T entity = query.getSingleResult();
        return Optional.ofNullable(entity);
    }
}
