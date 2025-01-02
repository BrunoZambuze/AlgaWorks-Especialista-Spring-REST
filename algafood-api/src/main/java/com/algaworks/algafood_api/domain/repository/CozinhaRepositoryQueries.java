package com.algaworks.algafood_api.domain.repository;

import com.algaworks.algafood_api.domain.model.Cozinha;

public interface CozinhaRepositoryQueries {

    Cozinha findByIdOrElseThrowException(Long cozinhaId);

}
