package com.algaworks.algafood_api.domain.service;

import com.algaworks.algafood_api.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood_api.domain.model.Cidade;
import com.algaworks.algafood_api.domain.model.Estado;
import com.algaworks.algafood_api.domain.repository.CidadeRepository;
import com.algaworks.algafood_api.domain.repository.EstadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CadastroCidadeService {

    @Autowired
    private CidadeRepository cidadeRepository;

    @Autowired
    private EstadoRepository estadoRepository;

    @Transactional
    public Cidade salvar(Cidade cidade){
        Long estadoId = cidade.getEstado().getId();

        Estado estado = estadoRepository.findByIdOrElseThrowException(estadoId);
        cidade.setEstado(estado);
        return cidadeRepository.save(cidade);

    }

    @Transactional
    public Cidade atualizar(Cidade cidade){

            Long estadoId = cidade.getEstado().getId();
            Estado estadoEncontrado = estadoRepository.findByIdOrElseThrowException(estadoId);

            cidade.setEstado(estadoEncontrado);

            cidade = cidadeRepository.save(cidade);
            return cidade;
    }

    @Transactional
    public void deletar(Long cidadeId){
        try {
            Cidade cidadeEncontrada = cidadeRepository.findByIdOrElseThrowException(cidadeId);
            cidadeRepository.delete(cidadeEncontrada);
            cidadeRepository.flush();
        } catch (DataIntegrityViolationException e) {
            throw new EntidadeEmUsoException("Cidade com id " + cidadeId + " não pode ser removida pois está sendo utilizado por outra classe");
        }

    }

}
