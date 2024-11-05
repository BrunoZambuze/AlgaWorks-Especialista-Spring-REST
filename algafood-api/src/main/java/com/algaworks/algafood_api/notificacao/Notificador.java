package com.algaworks.algafood_api.notificacao;

import com.algaworks.algafood_api.model.Cliente;

public interface Notificador {
    void notificar(Cliente cliente, String mensagem);
}
