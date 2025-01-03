package com.algaworks.algafood_api.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class EstadoNaoEncontradoException extends EntidadeNaoEncontradaException{
    public EstadoNaoEncontradoException(String message) {
        super("Estado", message);
    }
}
