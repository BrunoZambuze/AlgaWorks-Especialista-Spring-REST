package com.algaworks.algafood_api.api.model.representationmodel;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class RestauranteModel {

    private Long id;
    private String nome;
    /*
        No modelo de origem a propriedade está escrita como "taxaFrete", porém no DTO estamos chamando ela de "precoFrete". Quando fazemos o
        model mapper, a correspondência não é executada, pois a inteligência do model mapper não identifica a propriedade "taxaFrete" pois seu
        nome é diferente. Seria muito mais fácil só alterarmos o nome da propriedade atual, mas vamos supor que realmente precisamos manter esse nome.
        Para resolver esse problema devemos ir até a pasta 'core' na classe 'ModelMapperConfig' onde está localizado o Bean (instanciação) do model
        mapper e adicionar uma nova configuração para iniciar o mapeamento dos tipos.
     */
    private BigDecimal precoFrete;
    private CozinhaModel cozinha;

}
