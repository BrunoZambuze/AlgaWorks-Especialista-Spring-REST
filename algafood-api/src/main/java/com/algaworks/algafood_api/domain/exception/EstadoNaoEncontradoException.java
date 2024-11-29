package com.algaworks.algafood_api.domain.exception;

public class EstadoNaoEncontradoException extends EntidadeNaoEncontradaException{
    public EstadoNaoEncontradoException(String message) {
        super("Estado", message);
    }
}
