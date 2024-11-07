package com.algaworks.algafood_api.notificacao;

import com.algaworks.algafood_api.model.Cliente;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Qualifier("email")
@Component
public class NotificadorEmail implements Notificador {

    @Override
    public void notificar(Cliente cliente, String mensagem){
        System.out.printf("%s foi enviado uma mensagem para o e-mail '%s': %s",
                          cliente.getNome(), cliente.getEmail(), mensagem);
    }

}
