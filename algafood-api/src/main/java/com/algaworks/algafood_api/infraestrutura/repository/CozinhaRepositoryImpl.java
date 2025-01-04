package com.algaworks.algafood_api.infraestrutura.repository;

import com.algaworks.algafood_api.domain.exception.CozinhaNaoEncontradaException;
import com.algaworks.algafood_api.domain.model.Cozinha;
import com.algaworks.algafood_api.domain.repository.CozinhaRepository;
import com.algaworks.algafood_api.domain.repository.CozinhaRepositoryQueries;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Repository;

@Repository
public class CozinhaRepositoryImpl implements CozinhaRepositoryQueries {

    @Lazy
    @Autowired
    private CozinhaRepository cozinhaRepository;

    @Override
    public Cozinha findByIdOrElseThrowException(Long cozinhaId){
        return cozinhaRepository.findById(cozinhaId)
                .orElseThrow(() -> new CozinhaNaoEncontradaException(cozinhaId));
    }

}
