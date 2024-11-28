package com.algaworks.algafood_api.infraestrutura.repository;

import com.algaworks.algafood_api.domain.model.Estado;
import com.algaworks.algafood_api.domain.repository.EstadoRepository;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Component
public class EstadoImpl implements EstadoRepository {

    @PersistenceContext
    EntityManager manager;

    @Override
    public List<Estado> listar() {
        return manager.createQuery("from Estado", Estado.class).getResultList();
    }

    @Override
    public Estado buscar(Long id) {
        Estado estadoEncontrado = manager.find(Estado.class, id);
        if(estadoEncontrado == null){
            throw new EmptyResultDataAccessException(1);
        }
        return estadoEncontrado;
    }

    @Override
    @Transactional
    public Estado salvar(Estado estado) {
        return manager.merge(estado);
    }

    @Override
    @Transactional
    public void remover(Estado estado) {
        Estado estadoEncontrado = this.buscar(estado.getId());
        manager.remove(estadoEncontrado);
    }
}
