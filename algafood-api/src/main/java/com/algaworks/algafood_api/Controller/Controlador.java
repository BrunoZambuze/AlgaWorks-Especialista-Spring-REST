package com.algaworks.algafood_api.Controller;

import com.algaworks.algafood_api.model.Cliente;
import com.algaworks.algafood_api.service.AtivacaoClienteService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class Controlador {

    private AtivacaoClienteService ativacaoClienteService;

    public Controlador(AtivacaoClienteService ativacaoClienteService){
        this.ativacaoClienteService = ativacaoClienteService;
    }

    @GetMapping("/hello")
    @ResponseBody
    public String hello(){
        Cliente cliente = new Cliente("Bruno Silva", "bruno@hotmail.com", "991828372");

        ativacaoClienteService.ativar(cliente);

        return "Olá mundo!";
    }

}
