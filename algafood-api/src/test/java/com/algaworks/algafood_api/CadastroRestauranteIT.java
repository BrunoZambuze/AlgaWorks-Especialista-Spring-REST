package com.algaworks.algafood_api;

import com.algaworks.algafood_api.domain.repository.RestauranteRepository;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.TestPropertySource;
import org.hamcrest.Matchers;
import util.ResourceUtils;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("/application-test.properties")
public class CadastroRestauranteIT {

    @LocalServerPort
    private int randomPort;

    @Autowired
    private Flyway flyway;

    @Autowired
    private RestauranteRepository restauranteRepository;

    private int quantidadeTotalDeRestaurantes;
    private int idRestauranteCorreto = 1;
    private int idRestauranteErrado = 1000;
    private int quantidadeTotalGetUmUnicoItem = 1;
    private String jsonCorretoRestaurante;
    private String jsonIncorretoRestauranteComCozinhaInexistente;

    @BeforeEach
    public void setUp(){
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        RestAssured.port = randomPort;
        RestAssured.basePath = "/restaurantes";

        flyway.migrate();

        getQuantidadeTotalDeRestaurantes();
    }

    @Test
    public void deveRetornar200_QuandoBuscarTodosOsRestaurantes(){
        RestAssured
                .given()
                    .accept(ContentType.JSON)
                .when()
                    .get()
                .then()
                    .statusCode(HttpStatus.OK.value());
    }

    @Test
    public void deveRetornarStatus200_QuandoBuscarPeloId(){
        RestAssured
                .given()
                    .pathParam("restauranteId", idRestauranteCorreto)
                    .accept(ContentType.JSON)
                .when()
                    .get("/{restauranteId}")
                .then()
                    .statusCode(HttpStatus.OK.value())
                    .body("id", Matchers.equalTo(quantidadeTotalGetUmUnicoItem));
    }

    @Test
    public void deveRetornarStatus404_QuandoBuscarPorIdInexistente(){
        RestAssured
                .given()
                    .pathParam("restauranteId", idRestauranteErrado)
                    .accept(ContentType.JSON)
                .when()
                    .get("/{restauranteId}")
                .then()
                    .statusCode(HttpStatus.NOT_FOUND.value());
    }

    @Test
    public void deveRetornarStatus201_QuandoCadastrarRestaurante(){
        jsonCorretoRestaurante = ResourceUtils.getConteudoDoRecurso("/json/incorreto/restaurante-new-york-barbecue.json");

        RestAssured
                .given()
                    .body(jsonCorretoRestaurante)
                    .contentType(ContentType.JSON)
                    .accept(ContentType.JSON)
                .when()
                    .post()
                .then()
                    .statusCode(HttpStatus.CREATED.value())
                    .body("nome", Matchers.equalTo("New York Barbecue"));
    }

    @Test
    public void deveRetornarStatus400_QuandoCadastrarRestauranteComCozinhaInexistente(){
        jsonIncorretoRestauranteComCozinhaInexistente = ResourceUtils.getConteudoDoRecurso
                            ("/json/incorreto/restaurante-new-york-barbecue-com-cozinha-inexistente.json");

        RestAssured
                .given()
                    .body(jsonIncorretoRestauranteComCozinhaInexistente)
                    .contentType(ContentType.JSON)
                    .accept(ContentType.JSON)
                .when()
                    .post()
                .then()
                    .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    public void deveRetornarStatus400_QuandoCadastrarRestauranteSemCozinha(){
        jsonIncorretoRestauranteComCozinhaInexistente = ResourceUtils.getConteudoDoRecurso
                ("/json/incorreto/restaurante-new-york-barbecue-sem-cozinha.json");

        RestAssured
                .given()
                    .body(jsonIncorretoRestauranteComCozinhaInexistente)
                    .contentType(ContentType.JSON)
                    .accept(ContentType.JSON)
                .when()
                    .post()
                    .then()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    public void deveRetornarStatus400_QuandoCadastrarRestauranteSemTaxaFrete(){
        jsonIncorretoRestauranteComCozinhaInexistente = ResourceUtils.getConteudoDoRecurso
                ("/json/incorreto/restaurante-new-york-barbecue-sem-frete.json");

        RestAssured
                .given()
                    .body(jsonIncorretoRestauranteComCozinhaInexistente)
                    .contentType(ContentType.JSON)
                    .accept(ContentType.JSON)
                .when()
                    .post()
                .then()
                    .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    private void getQuantidadeTotalDeRestaurantes(){
        quantidadeTotalDeRestaurantes = restauranteRepository.findAll().size();
    }

}
