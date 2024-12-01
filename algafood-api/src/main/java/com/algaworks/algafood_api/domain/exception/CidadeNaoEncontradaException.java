package com.algaworks.algafood_api.domain.exception;

public class CidadeNaoEncontradaException extends EntidadeNaoEncontradaException{
    public CidadeNaoEncontradaException(String message) {
        super("Cidade", message);
    }
}
