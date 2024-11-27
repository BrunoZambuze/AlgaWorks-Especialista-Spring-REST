package com.algaworks.algafood_api.domain.exception;

public class CozinhaNaoEncontradaException extends RuntimeException {
    public CozinhaNaoEncontradaException(String message) {
        super(message);
    }
}
