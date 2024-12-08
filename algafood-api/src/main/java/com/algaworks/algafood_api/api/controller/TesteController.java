package com.algaworks.algafood_api.api.controller;

import com.algaworks.algafood_api.domain.model.Cozinha;
import com.algaworks.algafood_api.domain.model.Restaurante;
import com.algaworks.algafood_api.domain.repository.CozinhaRepository;
import com.algaworks.algafood_api.domain.repository.RestauranteRepository;
import com.algaworks.algafood_api.infraestrutura.repository.specification.RestauranteSpecs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/teste")
public class TesteController {

    @Autowired
    private CozinhaRepository cozinhaRepository;

    @Autowired
    private RestauranteRepository restauranteRepository;

    @GetMapping("/cozinhas/por-nome")
    public List<Cozinha> buscarPorNome(@RequestParam("nome") String nomeCozinha){
        return cozinhaRepository.findTodasByNomeContaining(nomeCozinha);
    }

    @GetMapping("/restaurantes/por-taxa-frete")
    public List<Restaurante> buscarPorTaxaFrete(@RequestParam BigDecimal taxaInicial,
                                                @RequestParam BigDecimal taxaFinal){
        return restauranteRepository.findByTaxaFreteBetween(taxaInicial, taxaFinal);
    }

    @GetMapping("/restaurantes/por-nome-e-cozinha")
    public List<Restaurante> buscarPorRestauranteAndCozinha(@RequestParam("nome") String nomeRestaurante,
                                                            @RequestParam("id") Long cozinhaId){
        return restauranteRepository.consultarPorNome(nomeRestaurante, cozinhaId);
    }

    @GetMapping("/restaurantes/primeiro-por-nome")
    public Optional<Restaurante> buscarPrimeiroRestaurantePorNome(@RequestParam String nome){
        return restauranteRepository.findFirstRestauranteByNomeContaining(nome);
    }

    @GetMapping("/restaurantes/dois-por-nome")
    public List<Restaurante> buscarOsDoisPrimeirosRestaurantesPorNome(String nome){
        return restauranteRepository.findTop2ByNomeContaining(nome);
    }

    @GetMapping("/restaurantes/count-por-cozinha")
    public int countPorCozinha(@RequestParam("id") Long cozinhaId){
        return restauranteRepository.countByCozinhaId(cozinhaId);
    }

    @GetMapping("/restaurantes/por-nome-e-frete")
    public List<Restaurante> buscarPorNomeFrete(@RequestParam(required = false) String nome,
                                                @RequestParam(value = "inicial", required = false) BigDecimal freteInicial,
                                                @RequestParam(value = "final", required = false)  BigDecimal freteFinal){
        return restauranteRepository.find(nome, freteInicial, freteFinal);
    }

    @GetMapping("/restaurantes/com-frete-gratis")
    public List<Restaurante> buscarComFreteGratis(@RequestParam String nome){
        return restauranteRepository.findComFreteGratis(nome);
    }

}
