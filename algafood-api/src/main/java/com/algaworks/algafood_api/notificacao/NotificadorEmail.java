package com.algaworks.algafood_api.notificacao;

import com.algaworks.algafood_api.model.Cliente;

public class NotificadorEmail implements Notificador {

    private boolean caixaAlta;
    private String hostServidorSmtp;

    public NotificadorEmail(String hostServidorSmtp){
        this.hostServidorSmtp = hostServidorSmtp;
    }

    @Override
    public void notificar(Cliente cliente, String mensagem){
        if(this.caixaAlta){
            mensagem = mensagem.toUpperCase();
        }
        System.out.printf("%s foi enviado uma mensagem para o email '%s' usando Smtp %s : %s",
                          cliente.getNome(), cliente.getEmail(), this.hostServidorSmtp, mensagem);
    }

    public void setCaixaAlta(boolean caixaAlta){
        this.caixaAlta = caixaAlta;
    }

}
