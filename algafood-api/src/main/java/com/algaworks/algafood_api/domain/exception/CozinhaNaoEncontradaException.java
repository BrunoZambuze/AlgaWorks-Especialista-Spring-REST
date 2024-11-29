package com.algaworks.algafood_api.domain.exception;

public class CozinhaNaoEncontradaException extends EntidadeNaoEncontradaException {

    public CozinhaNaoEncontradaException(String message) {
        super("Cozinha", message);
    }

}
