package com.algaworks.algafood_api.listener;

import com.algaworks.algafood_api.notificacao.NivelUrgencia;
import com.algaworks.algafood_api.notificacao.Notificador;
import com.algaworks.algafood_api.notificacao.TipoDoNotificador;
import com.algaworks.algafood_api.service.ClienteAtivadoEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class NotificacaoService {

    @TipoDoNotificador(value = NivelUrgencia.NORMAL)
    @Autowired
    private Notificador notificador;

    @EventListener //Estamos informando ao Spring que esse método está "ouvindo" se tem algum evento do ClienteAtivado
    public void clienteAtivadoListener(ClienteAtivadoEvent event){
        notificador.notificar(event.getCliente(), "Seu cadastro no sistema está ativo!");
    }

}
