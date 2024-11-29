package com.algaworks.algafood_api.domain.service;

import com.algaworks.algafood_api.domain.exception.CozinhaNaoEncontradaException;
import com.algaworks.algafood_api.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood_api.domain.exception.RestauranteNaoEncontradoException;
import com.algaworks.algafood_api.domain.model.Cozinha;
import com.algaworks.algafood_api.domain.model.Restaurante;
import com.algaworks.algafood_api.domain.repository.CozinhaRepository;
import com.algaworks.algafood_api.domain.repository.RestauranteRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CadastroRestauranteService {

    @Autowired
    private RestauranteRepository restauranteRepository;

    @Autowired
    private CozinhaRepository cozinhaRepository;

    public Restaurante salvar(Restaurante restaurante){
        Long cozinhaId = restaurante.getCozinha().getId();
        Cozinha cozinhaEncontrada = cozinhaRepository.buscar(cozinhaId);
        if(cozinhaEncontrada == null){
            throw new CozinhaNaoEncontradaException(String.format("Não existe cadastro de cozinha com o código %d", cozinhaId));
        }
        restaurante.setCozinha(cozinhaEncontrada);
        return restauranteRepository.salvar(restaurante);
    }

    public Restaurante atualizar(Long restauranteId,
                                 Restaurante restauranteAlterar){
        Restaurante restauranteEncontrado = restauranteRepository.buscar(restauranteId);
        Long cozinhaId = restauranteAlterar.getCozinha().getId();
        Cozinha cozinhaEncontrada = cozinhaRepository.buscar(cozinhaId);

        if(restauranteEncontrado == null){
            throw new RestauranteNaoEncontradoException(String.format("Não existe cadastro de restaurante com id %d", restauranteId));
        }
        if(cozinhaEncontrada == null){
            throw new CozinhaNaoEncontradaException(String.format("Não existe cadastro de cozinha com id %d", cozinhaId));
        }

        BeanUtils.copyProperties(restauranteAlterar, restauranteEncontrado, "id");
        restauranteEncontrado.setCozinha(cozinhaEncontrada);

        restauranteEncontrado = restauranteRepository.salvar(restauranteEncontrado);
        return restauranteEncontrado;

    }

}