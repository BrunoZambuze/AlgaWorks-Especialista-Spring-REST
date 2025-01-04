package com.algaworks.algafood_api.domain.exception;

public class CozinhaNaoEncontradaException extends EntidadeNaoEncontradaException {

    public CozinhaNaoEncontradaException(String message) {
        super(message);
    }

    public CozinhaNaoEncontradaException(Long cozinhaId){
        this(String.format("Não existe cadastro de cozinha com %d", cozinhaId));
    }

}
