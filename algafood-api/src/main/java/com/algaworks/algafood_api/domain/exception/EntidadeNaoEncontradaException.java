package com.algaworks.algafood_api.domain.exception;

import lombok.Getter;
import net.bytebuddy.implementation.bind.annotation.Super;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.server.ResponseStatusException;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
@Getter
public class EntidadeNaoEncontradaException extends RuntimeException {

    private final String nomeEntidade;

    public EntidadeNaoEncontradaException(String nomeEntidade, String mensagem) {
        super(mensagem);
        this.nomeEntidade = nomeEntidade;
    }

}
