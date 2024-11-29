package com.algaworks.algafood_api.domain.exception;

public class RestauranteNaoEncontradoException extends EntidadeNaoEncontradaException{

    public RestauranteNaoEncontradoException(String message) {
        super("Restaurante", message);
    }

}
