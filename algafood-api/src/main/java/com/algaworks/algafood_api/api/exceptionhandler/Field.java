package com.algaworks.algafood_api.api.exceptionhandler;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Field {

    private String name;
    private String uiMessage;

}
