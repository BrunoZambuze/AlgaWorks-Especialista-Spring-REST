package com.algaworks.algafood_api.domain.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
@Getter
public abstract class EntidadeNaoEncontradaException extends NegocioException {

    public EntidadeNaoEncontradaException(String mensagem) {
        super(mensagem);
    }

}
