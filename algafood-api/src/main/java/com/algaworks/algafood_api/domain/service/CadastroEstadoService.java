package com.algaworks.algafood_api.domain.service;

import com.algaworks.algafood_api.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood_api.domain.model.Estado;
import com.algaworks.algafood_api.domain.repository.EstadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CadastroEstadoService {

    @Autowired
    private EstadoRepository estadoRepository;

    @Transactional
    public Estado adicionar(Estado estado){
        return estadoRepository.save(estado);
    }

    @Transactional
    public Estado atualizar(Estado estadoAlterar){

            estadoAlterar = estadoRepository.save(estadoAlterar);
            return estadoAlterar;

    }

    @Transactional
    public void remover(Long estadoId){
        try{
            estadoRepository.findByIdOrElseThrowException(estadoId);
            estadoRepository.deleteById(estadoId);
            estadoRepository.flush();
        } catch (DataIntegrityViolationException e) {
            throw new EntidadeEmUsoException("Não é possível remover o estado com id " + estadoId + " pois está sendo utilizado por outra classe");
        }
    }

}
