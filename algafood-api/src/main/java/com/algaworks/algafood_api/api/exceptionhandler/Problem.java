package com.algaworks.algafood_api.api.exceptionhandler;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;

@JsonInclude(JsonInclude.Include.NON_NULL) //Incluir na representação as propriedades que não estiverem nulas
@Getter
@Builder // Padrão de projeto Builder
public class Problem {

    private Integer status;
    private String type;
    private String title;
    private String detail;

}