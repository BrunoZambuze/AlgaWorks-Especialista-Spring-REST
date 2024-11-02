package com.algaworks.algafood_api.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class Controlador {

    @GetMapping("/hello")
    @ResponseBody
    public String hello(){
        return "Olá mundo!";
    }

}
