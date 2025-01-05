package com.algaworks.algafood_api.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public class RestauranteNaoEncontradoException extends EntidadeNaoEncontradaException{

    public RestauranteNaoEncontradoException(String message) {
        super(message);
    }

    public RestauranteNaoEncontradoException(Long restauranteId){
        this(String.format("Não existe cadastro de restaurante com id %d", restauranteId));
    }

}
