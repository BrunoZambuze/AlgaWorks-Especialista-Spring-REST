package com.algaworks.algafood_api.notificacao;

import com.algaworks.algafood_api.model.Cliente;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class NotificadorSMS implements Notificador {

//    @Qualifier("sms")
    @TipoDoNotificador(value = NivelUrgencia.URGENTE)
    @Override
    public void notificar(Cliente cliente, String mensagem){
        System.out.printf("%s foi enviado uma mensagem para o telefone '%s': %s",
                          cliente.getNome(), cliente.getTelefone(), mensagem);
    }

}
