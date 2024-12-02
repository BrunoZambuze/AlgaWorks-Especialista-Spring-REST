package com.algaworks.algafood_api.domain.service;

import com.algaworks.algafood_api.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood_api.domain.exception.EstadoNaoEncontradoException;
import com.algaworks.algafood_api.domain.model.Estado;
import com.algaworks.algafood_api.domain.repository.EstadoRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
public class CadastroEstadoService {

    @Autowired
    private EstadoRepository estadoRepository;

    public Estado adicionar(Estado estado){
        return estadoRepository.save(estado);
    }

    public Estado atualizar(Long estadoId, Estado estadoAlterar){

            Estado estadoEncontrado = estadoRepository.findById(estadoId).orElseThrow(()
                    -> new EstadoNaoEncontradoException(String.format("Não existe cadastro de estado com o id %d", estadoId)));

            BeanUtils.copyProperties(estadoAlterar, estadoEncontrado, "id");
            estadoEncontrado = estadoRepository.save(estadoEncontrado);
            return estadoEncontrado;

    }

    public void remover(Long estadoId){
        try{
            if(!estadoRepository.existsById(estadoId)){
                throw new EstadoNaoEncontradoException(String.format("Não existe cadastro de estado com o id %d", estadoId));
            }
            estadoRepository.deleteById(estadoId);
        } catch (DataIntegrityViolationException e) {
            throw new EntidadeEmUsoException(String.format("Entidade com id %d não pode ser removida, pois está em uso", estadoId));
        }
    }

}
