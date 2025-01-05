package com.algaworks.algafood_api.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public class EstadoNaoEncontradoException extends EntidadeNaoEncontradaException{
    public EstadoNaoEncontradoException(String message) {
        super(message);
    }

    public EstadoNaoEncontradoException(Long estadoId){
        this(String.format("Não foi encontrado nenhum estado com id %d", estadoId));
    }

}
