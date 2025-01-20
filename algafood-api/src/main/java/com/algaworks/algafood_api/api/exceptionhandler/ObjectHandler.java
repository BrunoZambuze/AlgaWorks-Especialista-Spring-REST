package com.algaworks.algafood_api.api.exceptionhandler;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ObjectHandler {

    private String name;
    private String uiMessage;

}
