package com.algaworks.algafood_api.Controller;

import com.algaworks.algafood_api.notificacao.Notificador;
import com.algaworks.algafood_api.service.AtivacaoClienteService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServiceConfig {

    @Bean
    public AtivacaoClienteService ativacaoClienteService(Notificador notificador){
        return new AtivacaoClienteService(notificador);
    }

}
