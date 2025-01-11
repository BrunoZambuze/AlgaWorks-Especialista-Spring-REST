package com.algaworks.algafood_api.domain.service;

import com.algaworks.algafood_api.domain.exception.RestauranteNaoEncontradoException;
import com.algaworks.algafood_api.domain.model.Cozinha;
import com.algaworks.algafood_api.domain.model.Restaurante;
import com.algaworks.algafood_api.domain.repository.CozinhaRepository;
import com.algaworks.algafood_api.domain.repository.RestauranteRepository;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.IgnoredPropertyException;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Field;
import java.util.Map;

@Service
public class CadastroRestauranteService {

    @Autowired
    private RestauranteRepository restauranteRepository;

    @Autowired
    private CozinhaRepository cozinhaRepository;


    public Restaurante buscarOuFalhar(Long restauranteId){
        return restauranteRepository.findById(restauranteId)
                .orElseThrow(() -> new RestauranteNaoEncontradoException(restauranteId));
    }

    public Restaurante salvar(Restaurante restaurante){
        Long cozinhaId = restaurante.getCozinha().getId();
        Cozinha cozinhaEncontrada = cozinhaRepository.findByIdOrElseThrowException(cozinhaId);


        restaurante.setCozinha(cozinhaEncontrada);

        return restauranteRepository.save(restaurante);
    }

    public Restaurante atualizar(Long restauranteId,
                                 Restaurante restauranteAlterar){
        Restaurante restauranteEncontrado = buscarOuFalhar(restauranteId);
        Long cozinhaId = restauranteAlterar.getCozinha().getId();
        Cozinha cozinhaEncontrada = cozinhaRepository.findByIdOrElseThrowException(cozinhaId);

        BeanUtils.copyProperties(restauranteAlterar, restauranteEncontrado, "id", "formasPagamento", "endereco", "dataCadastro", "produtos");
        restauranteEncontrado.setCozinha(cozinhaEncontrada);

        restauranteEncontrado = restauranteRepository.save(restauranteEncontrado);
        return restauranteEncontrado;

    }

    public Restaurante atualizarParcial(Long restauranteId, Map<String, Object> campos, HttpServletRequest request){

        Long cozinhaId;

        Restaurante restauranteEncontrado = buscarOuFalhar(restauranteId);

       cozinhaId = restauranteEncontrado.getCozinha().getId();

       merge(campos, restauranteEncontrado, request);

       return atualizar(restauranteId, restauranteEncontrado);
    }

    public void merge(Map<String, Object> campos, Restaurante restauranteAtual, HttpServletRequest request){
        ObjectMapper objectMapper = new ObjectMapper();
        ServletServerHttpRequest serverHttpRequest = new ServletServerHttpRequest(request);
        try{
            objectMapper.configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, true);
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, true);
            Restaurante restauranteAlterar = objectMapper.convertValue(campos, Restaurante.class);

            campos.forEach((nomePropriedade, valorPropriedade) -> {
                Field fields = ReflectionUtils.findField(Restaurante.class, nomePropriedade);
                fields.setAccessible(true);
                Object novoValor = ReflectionUtils.getField(fields, restauranteAlterar);
                ReflectionUtils.setField(fields, restauranteAtual, novoValor);
            });
        } catch (IllegalArgumentException ex) {
            Throwable rootCause = ExceptionUtils.getRootCause(ex);
            throw new HttpMessageNotReadableException(ex.getMessage(), rootCause, serverHttpRequest);
        }
    }

}