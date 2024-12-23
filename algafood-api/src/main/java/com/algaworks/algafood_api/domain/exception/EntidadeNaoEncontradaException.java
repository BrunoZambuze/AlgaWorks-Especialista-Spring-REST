package com.algaworks.algafood_api.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class EntidadeNaoEncontradaException extends RuntimeException{

    private final String nomeEntidade;

    public EntidadeNaoEncontradaException(String nomeEntidade, String mensagem){
        super(mensagem);
        this.nomeEntidade = nomeEntidade;
    }

    public String getNomeEntidade(){
        return this.nomeEntidade;
    }

}
