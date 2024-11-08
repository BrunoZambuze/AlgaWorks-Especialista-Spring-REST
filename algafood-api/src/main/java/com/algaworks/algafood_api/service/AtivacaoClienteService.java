package com.algaworks.algafood_api.service;

import com.algaworks.algafood_api.model.Cliente;
import com.algaworks.algafood_api.notificacao.NivelUrgencia;
import com.algaworks.algafood_api.notificacao.Notificador;
import com.algaworks.algafood_api.notificacao.TipoDoNotificador;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class AtivacaoClienteService {

//    @Qualifier("email")
    @TipoDoNotificador(value = NivelUrgencia.NORMAL)
    @Autowired
    private Notificador notificador;

//    @Autowired
//    public AtivacaoClienteService(Notificador notificador){
//        this.notificador = notificador;
//    }

    public void ativar(Cliente cliente){
        cliente.ativar();
        notificador.notificar(cliente, "Seu cadastro no sistema está ativo!");
    }

}
