package com.algaworks.algafood_api.service;

import com.algaworks.algafood_api.model.Cliente;
import com.algaworks.algafood_api.notificacao.NivelUrgencia;
import com.algaworks.algafood_api.notificacao.Notificador;
import com.algaworks.algafood_api.notificacao.TipoDoNotificador;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class AtivacaoClienteService {

//  @Qualifier("email")
//    @TipoDoNotificador(value = NivelUrgencia.NORMAL)
//    @Autowired
//    private Notificador notificador;

//    @Autowired
//    public AtivacaoClienteService(Notificador notificador){
//        this.notificador = notificador;
//    }

    @Autowired
    private ApplicationEventPublisher eventPublisher;

    public void ativar(Cliente cliente){
        cliente.ativar();

        //Em vez de deixar a classe "AtivacaoClienteService" com a responsabilidade de enviar uma notificação para o usuário,
        // nós podemos usar o padrão de projetos 'Observer', que é implementado pelo tratamento de eventos do Spring. Assim nós
        // podemos remover essa responsabilidade da classe "ativar" de notificar o cliente, para manter somente a responsabiliadae
        // de ativar o cliente, depois mandar um evento informando que o cliente está ativo, assim uma outra classe irá "ouvir" esse evento
        // terá a responsabiliade de enviar a notificação para o usuário.

        //notificador.notificar(cliente, "Seu cadastro no sistema está ativo!");

        eventPublisher.publishEvent(new ClienteAtivadoEvent(cliente));

    }

}
