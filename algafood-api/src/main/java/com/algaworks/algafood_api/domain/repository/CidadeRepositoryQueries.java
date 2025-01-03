package com.algaworks.algafood_api.domain.repository;

import com.algaworks.algafood_api.domain.model.Cidade;

public interface CidadeRepositoryQueries {

    Cidade findByIdOrElseThrowException(Long cidadeId);

}