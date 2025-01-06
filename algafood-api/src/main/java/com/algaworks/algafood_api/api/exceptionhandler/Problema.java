package com.algaworks.algafood_api.api.exceptionhandler;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder // Padrão de projeto Builder
public class Problema {

    private LocalDateTime dataHora;
    private String mensagem;

}