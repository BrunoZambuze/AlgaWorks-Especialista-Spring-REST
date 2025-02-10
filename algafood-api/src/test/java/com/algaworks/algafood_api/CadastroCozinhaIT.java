package com.algaworks.algafood_api;

import com.algaworks.algafood_api.domain.model.Cozinha;
import com.algaworks.algafood_api.domain.repository.CozinhaRepository;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.flywaydb.core.Flyway;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.TestPropertySource;
import util.ResourceUtils;

import static org.hamcrest.Matchers.equalTo;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT) //Nosso teste da API precisa de um servidor para funcionar, por isso vamos utilizar
            // o WebEnvironment.RANDOM_PORT para subir um servidor em uma porta aleatória
@TestPropertySource("/application-test.properties") //A aplicação rodará o 'application.properties' e juntará com o 'application-test.properties'. As propriedades
                                                   //que forem diferentes em ambos os arquivos irão se manter e as propriedades "iguais" serão sobrescrevidas pelo
                                                  //'application-teste.properties'.
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

    @Autowired
    private Flyway flyway;

    @Autowired
    private CozinhaRepository cozinhaRepository;

    private static int quantidadeTotalDeCozinhas;
    private static final int COZINHA_ID_INEXISTENTE = 1000;
    private static final int COZINHA_ID_EXISTENTE = 2;
    private static String jsonCorretoCozinhaChinesa;

    @BeforeEach //Será um método de preparação. Ele será ativado em cada um dos métodos de teste
    public void setUp(){
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        RestAssured.port = randomPort;
        RestAssured.basePath = "/cozinhas";

        flyway.migrate();

        getQuantidadeTotalDeCozinhas();
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
    public void deveRetornarQuantidadeCorretaDeCozinhas_QuandoConsultarCozinhas(){
        RestAssured
                .given()
                    .accept(ContentType.JSON)
                .when()
                    .get()
                .then()
                    .body("id", Matchers.hasSize(quantidadeTotalDeCozinhas)) //Verifica se tem 4 itens
                    .body("nome", Matchers.hasItems("Brasileira", "Argentina")); //Verifica se tem as cozinhas: Brasileira e Argentina
    }

    @Test
    public void deveRetornarStatus201_QuandoCadastrarCozinha(){
        jsonCorretoCozinhaChinesa = ResourceUtils.getConteudoDoRecurso("/json/correto/cozinha-chinesa.json");

        RestAssured
                .given()
                    .body(jsonCorretoCozinhaChinesa)
                    .contentType(ContentType.JSON)
                    .accept(ContentType.JSON)
                .when()
                    .post()
                .then()
                    .statusCode(HttpStatus.CREATED.value());
    }

    @Test
    public void deveRetornarRespostaEStatusCorreto_QuandoConsultarCozinhaExistente(){
        RestAssured
                .given()
                    .pathParam("cozinhaId", COZINHA_ID_EXISTENTE)
                    .accept(ContentType.JSON)
                .when()
                    .get("/{cozinhaId}")
                .then()
                    .statusCode(HttpStatus.OK.value())
                    .body("nome", equalTo("Indiana"));
    }

    @Test
    public void deveRetornarStatus404_QuandoConsultarCozinhaInexistente(){
        RestAssured
                .given()
                    .pathParam("cozinhaId", COZINHA_ID_INEXISTENTE)
                    .accept(ContentType.JSON)
                .when()
                    .get("/{cozinhaId}")
                .then()
                    .statusCode(HttpStatus.NOT_FOUND.value());
    }

    private void getQuantidadeTotalDeCozinhas(){
        List<Cozinha> listaCozinhas = cozinhaRepository.findAll();
        quantidadeTotalDeCozinhas = listaCozinhas.size();
    }

}