package com.algaworks.algafood_api.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND) //<--- Não precisa colocar pois a classe pai já possui o status not_found,
                                     // mas vou manter para deixar explícito
public class RestauranteNaoEncontradoException extends EntidadeNaoEncontradaException{

    public RestauranteNaoEncontradoException(String message) {
        super(message);
    }

    public RestauranteNaoEncontradoException(Long restauranteId){
        this(String.format("Não existe cadastro de restaurante com id %d", restauranteId));
    }

}
