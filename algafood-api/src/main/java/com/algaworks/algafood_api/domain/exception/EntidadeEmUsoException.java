package com.algaworks.algafood_api.domain.exception;

public class EntidadeEmUsoException extends RuntimeException{
    public EntidadeEmUsoException(String mensagem){
        super(mensagem);
    }
}
