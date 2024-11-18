package com.algaworks.algafood_api.jpa;

import com.algaworks.algafood_api.domain.model.Cozinha;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Component
public class CadastroCozinha {

    @PersistenceContext
    EntityManager manager;

    public List<Cozinha> listar() {
        return manager.createQuery("from Cozinha", Cozinha.class).getResultList();
    }

    @Transactional
    public Cozinha adicionarCozinha(Cozinha cozinha){
        return manager.merge(cozinha);
    }

    public Cozinha buscarId(Long id){
        return manager.find(Cozinha.class, id);
    }

    @Transactional
    public void remover(Cozinha cozinha){
        cozinha = this.buscarId(cozinha.getId());
        manager.remove(cozinha);
    }

}
