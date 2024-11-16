package com.algaworks.algafood_api.notificacao;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("notificador.email") //Estamos dizendo ao Spring que essa classe representa um arquivo de configurações de propriedades
public class NotificadorProperties {

    /**
     * Host do servidor de e-mail
     */
    private String hostServidor; // <-- Precisa ter o mesmo nome que está no arquivo propertie

    /**
     * Porta do servidor de e-mail
     */
    private Integer portaServidor;

    public String getHostServidor(){
        return this.hostServidor;
    }

    public void setHostServidor(String hostServidor){
        this.hostServidor = hostServidor;
    }

    public Integer getPortaServidor(){
        return this.portaServidor;
    }

    public void setPortaServidor(Integer portaServidor){
        this.portaServidor = portaServidor;
    }

}
