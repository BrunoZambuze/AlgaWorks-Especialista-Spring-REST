package com.algaworks.algafood_api.domain.repository;

import com.algaworks.algafood_api.domain.model.Estado;

public interface EstadoRepositoryQueries {

    Estado findByEstadoOrElseThrowException(Long estadoId);

}
