package com.algaworks.algafood_api.domain.exception;

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
