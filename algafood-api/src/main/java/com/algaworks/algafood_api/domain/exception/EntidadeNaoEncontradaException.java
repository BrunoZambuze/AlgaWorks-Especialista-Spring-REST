package com.algaworks.algafood_api.domain.exception;

public class EntidadeNaoEncontradaException extends RuntimeException{
    public EntidadeNaoEncontradaException(String mensagem){
        super(mensagem);
    }
}
