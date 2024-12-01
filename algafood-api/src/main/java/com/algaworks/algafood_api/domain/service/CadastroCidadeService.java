package com.algaworks.algafood_api.domain.service;

import com.algaworks.algafood_api.domain.exception.CidadeNaoEncontradaException;
import com.algaworks.algafood_api.domain.exception.EstadoNaoEncontradoException;
import com.algaworks.algafood_api.domain.model.Cidade;
import com.algaworks.algafood_api.domain.model.Estado;
import com.algaworks.algafood_api.domain.repository.CidadeRepository;
import com.algaworks.algafood_api.domain.repository.EstadoRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

@Service
public class CadastroCidadeService {

    @Autowired
    private CidadeRepository cidadeRepository;

    @Autowired
    private EstadoRepository estadoRepository;

    public Cidade buscar(Long cidadeId){
        try{
         Cidade cidadeEncontrada = cidadeRepository.buscar(cidadeId);
         return cidadeEncontrada;
        } catch (EmptyResultDataAccessException e) {
            throw new CidadeNaoEncontradaException(String.format("Não foi encontrada uma entidade de cidade com id %d", cidadeId));
        }
    }

    public Cidade salvar(Cidade cidade){
        Long estadoId = cidade.getEstado().getId();
        try {
            Estado estadoEncontrado = estadoRepository.buscar(estadoId);
            return cidadeRepository.salvar(cidade);
        } catch (EmptyResultDataAccessException e)
        {
            throw new EstadoNaoEncontradoException(String.format("Não foi encontrado um estado com id %d", estadoId));
        }
    }

    public Cidade atualizar(Long cidadeId, Cidade cidadeAlterar){

            Cidade cidadeEncontrada = cidadeRepository.buscar(cidadeId);
            if(cidadeEncontrada == null) {
                throw new CidadeNaoEncontradaException(String.format("Não foi encontrada nenhuma entidade cidade com id %d", cidadeId));
            }

            Long estadoId = cidadeAlterar.getEstado().getId();
            Estado estadoEncontrado = estadoRepository.buscar(estadoId);

            if(estadoEncontrado == null) {
                throw new EstadoNaoEncontradoException(String.format("Não foi encontrado nenhuma entidade estado com id %d", estadoId));
            }

            BeanUtils.copyProperties(cidadeAlterar, cidadeEncontrada, "id");
            cidadeEncontrada.setEstado(estadoEncontrado);
            cidadeEncontrada = cidadeRepository.salvar(cidadeEncontrada);
            return cidadeEncontrada;
    }

    public void deletar(Long cidadeId){
        Cidade cidadeEncontrada = cidadeRepository.buscar(cidadeId);
        if(cidadeEncontrada == null){
            throw new CidadeNaoEncontradaException(String.format("Não foi encontrada a entidade cidade com id %d", cidadeId));
        }
        cidadeRepository.remover(cidadeEncontrada);
    }

}
