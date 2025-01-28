package com.algaworks.algafood_api;

import com.algaworks.algafood_api.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood_api.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood_api.domain.model.Cozinha;
import com.algaworks.algafood_api.domain.model.Restaurante;
import com.algaworks.algafood_api.domain.service.CadastroCozinhaService;
import com.algaworks.algafood_api.domain.service.CadastroRestauranteService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.validation.ConstraintViolationException;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class CadastroCozinhaIntegrationTest {

    @Autowired
    private CadastroCozinhaService cadastroCozinhaService;

    @Autowired
    private CadastroRestauranteService cadastroRestauranteService;

    @Test
    public void deveAtribuirId_QuandoCadastrarCozinhaComDadosCorretos(){
        //Cenário
        Cozinha novaCozinha = new Cozinha();
        novaCozinha.setNome("Chinesa");

        //Ação
        novaCozinha = cadastroCozinhaService.salvar(novaCozinha);

        //Validação
        assertThat(novaCozinha).isNotNull();
        assertThat(novaCozinha.getId()).isNotNull();
        assertThat(novaCozinha.getNome()).isEqualTo("Chinesa");

    }

    @Test
    public void deveFalhar_QuandoCadastrarCozinhaSemNome(){

        Cozinha novaCozinha = new Cozinha();
        novaCozinha.setNome(null);

        ConstraintViolationException erroEncontrado = Assertions.assertThrows(ConstraintViolationException.class,
                                                                () -> cadastroCozinhaService.salvar(novaCozinha));

        assertThat(erroEncontrado).isNotNull();
    }

    @Test
    public void deveFalhar_QuandoExcluirCozinhaEmUso(){

        EntidadeEmUsoException erroEsperado = Assertions.assertThrows(EntidadeEmUsoException.class,
                () -> cadastroCozinhaService.excluir(1L));

        assertThat(erroEsperado).isNotNull();

    }

    @Test
    public void deveFalhar_QuandoExcluirCozinhaInexistente(){

        EntidadeNaoEncontradaException erroEsperado = Assertions.assertThrows(EntidadeNaoEncontradaException.class,
                () -> cadastroCozinhaService.excluir(1000L));

        assertThat(erroEsperado).isNotNull();

    }

}
