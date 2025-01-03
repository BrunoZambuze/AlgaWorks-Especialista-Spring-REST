package com.algaworks.algafood_api.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class CidadeNaoEncontradaException extends EntidadeNaoEncontradaException{
    public CidadeNaoEncontradaException(String message) {
        super("Cidade", message);
    }
}
