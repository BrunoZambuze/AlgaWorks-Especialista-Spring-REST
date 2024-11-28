package com.algaworks.algafood_api.domain.service;

import com.algaworks.algafood_api.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood_api.domain.model.Estado;
import com.algaworks.algafood_api.domain.repository.EstadoRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

@Service
public class CadastroEstadoService {

    @Autowired
    private EstadoRepository estadoRepository;

    public Estado adicionar(Estado estado){
        return estadoRepository.salvar(estado);
    }

    public Estado atualizar(Long estadoId, Estado estadoAlterar){
        try{
            Estado estadoEncontrado = estadoRepository.buscar(estadoId);
            BeanUtils.copyProperties(estadoAlterar, estadoEncontrado, "id");
            estadoEncontrado = estadoRepository.salvar(estadoEncontrado);
            return estadoEncontrado;
        } catch (EmptyResultDataAccessException e) {
            throw new EntidadeNaoEncontradaException(String.format("Não existe cadastro de estado com o id %d", estadoId));
        }
    }

}
