package com.algaworks.algafood_api.notificacao;

import com.algaworks.algafood_api.model.Cliente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

//@Primary
//@Qualifier("email")
@TipoDoNotificador(value = NivelUrgencia.NORMAL)
@Component
public class NotificadorEmail implements Notificador {

    @Autowired
    private NotificadorProperties properties;

    @Override
    public void notificar(Cliente cliente, String mensagem){
        System.out.println("Host: " + properties.getHostServidor());
        System.out.println("Porta: " + properties.getPortaServidor());

        System.out.printf("%s foi enviado uma mensagem para o e-mail '%s': %s",
                          cliente.getNome(), cliente.getEmail(), mensagem);
    }

}
