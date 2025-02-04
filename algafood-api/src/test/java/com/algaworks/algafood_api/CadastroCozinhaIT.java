package com.algaworks.algafood_api;

import com.algaworks.algafood_api.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood_api.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood_api.domain.model.Cozinha;
import com.algaworks.algafood_api.domain.service.CadastroCozinhaService;
import com.algaworks.algafood_api.domain.service.CadastroRestauranteService;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;

import javax.validation.ConstraintViolationException;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT) //Nosso teste da API precisa de um servidor para funcionar, por isso vamos utilizar
            // o WebEnvironment.RANDOM_PORT para subir um servidor em uma porta aleatória
class CadastroCozinhaIT {

    //TESTE DE INTEGRAÇÃO
//    @Autowired
//    private CadastroCozinhaService cadastroCozinhaService;
//
//    @Autowired
//    private CadastroRestauranteService cadastroRestauranteService;
//
//    @Test
//    public void deveAtribuirId_QuandoCadastrarCozinhaComDadosCorretos(){
//        //Cenário
//        Cozinha novaCozinha = new Cozinha();
//        novaCozinha.setNome("Chinesa");
//
//        //Ação
//        novaCozinha = cadastroCozinhaService.salvar(novaCozinha);
//
//        //Validação
//        assertThat(novaCozinha).isNotNull();
//        assertThat(novaCozinha.getId()).isNotNull();
//        assertThat(novaCozinha.getNome()).isEqualTo("Chinesa");
//
//    }
//
//    @Test
//    public void deveFalhar_QuandoCadastrarCozinhaSemNome(){
//
//        Cozinha novaCozinha = new Cozinha();
//        novaCozinha.setNome(null);
//
//        ConstraintViolationException erroEncontrado = Assertions.assertThrows(ConstraintViolationException.class,
//                                                                () -> cadastroCozinhaService.salvar(novaCozinha));
//
//        assertThat(erroEncontrado).isNotNull();
//    }
//
//    @Test
//    public void deveFalhar_QuandoExcluirCozinhaEmUso(){
//
//        EntidadeEmUsoException erroEsperado = Assertions.assertThrows(EntidadeEmUsoException.class,
//                () -> cadastroCozinhaService.excluir(1L));
//
//        assertThat(erroEsperado).isNotNull();
//
//    }
//
//    @Test
//    public void deveFalhar_QuandoExcluirCozinhaInexistente(){
//
//        EntidadeNaoEncontradaException erroEsperado = Assertions.assertThrows(EntidadeNaoEncontradaException.class,
//                () -> cadastroCozinhaService.excluir(1000L));
//
//        assertThat(erroEsperado).isNotNull();
//
//    }

    //TESTE DE API

    @LocalServerPort //Essa anotação irá capturar a porta aleatória que o nosso WebEnvironment utilizou para subir o servidor e irá jogar para a variável
    private int randomPort;

    @BeforeEach //Será um método de preparação. Ele será ativado em cada um dos métodos de teste
    public void setUp(){
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        RestAssured.port = randomPort;
        RestAssured.basePath = "/cozinhas";
    }

    @Test
    public void deveRetornarStatus200_QuandoConsultarCozinhas(){
        RestAssured
                .given()
                    .accept(ContentType.JSON)
                .when()
                    .get()
                .then()
                    .statusCode(HttpStatus.OK.value());
    }

    @Test
    public void deveConter4Cozinhas_QuandoConsultarCozinhas(){
        RestAssured
                .given()
                    .accept(ContentType.JSON)
                .when()
                    .get()
                .then()
                    .body("id", Matchers.hasSize(4)) //Verifica se tem 4 itens
                    .body("nome", Matchers.hasItems("Brasileira", "Argentina")); //Verifica se tem as cozinhas: Brasileira e Argentina
    }

}
