package com.algaworks.algafood_api.notificacao;

import org.springframework.beans.factory.annotation.Qualifier;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME) //
@Qualifier //Utilizamos essa anotação para dizer ao Spring que essa nossa anotação personalizada também é um qualificador
public @interface TipoDoNotificador {

    //O que vai diferenciar nossos Beans vai ser o NivelUrgencia
    NivelUrgencia value();

}
