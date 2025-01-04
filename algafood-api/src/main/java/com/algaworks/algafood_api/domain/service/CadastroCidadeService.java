package com.algaworks.algafood_api.domain.service;

import com.algaworks.algafood_api.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood_api.domain.model.Cidade;
import com.algaworks.algafood_api.domain.model.Estado;
import com.algaworks.algafood_api.domain.repository.CidadeRepository;
import com.algaworks.algafood_api.domain.repository.EstadoRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
public class CadastroCidadeService {

    @Autowired
    private CidadeRepository cidadeRepository;

    @Autowired
    private EstadoRepository estadoRepository;

    public Cidade salvar(Cidade cidade){
        Long estadoId = cidade.getEstado().getId();

        Estado estado = estadoRepository.findByIdOrElseThrowException(estadoId);
        cidade.setEstado(estado);
        return cidadeRepository.save(cidade);

    }

    public Cidade atualizar(Long cidadeId, Cidade cidadeAlterar){

            Cidade cidadeEncontrada = cidadeRepository.findByIdOrElseThrowException(cidadeId);

            Long estadoId = cidadeAlterar.getEstado().getId();
            Estado estadoEncontrado = estadoRepository.findByIdOrElseThrowException(estadoId);

            BeanUtils.copyProperties(cidadeAlterar, cidadeEncontrada, "id");
            cidadeEncontrada.setEstado(estadoEncontrado);
            cidadeEncontrada = cidadeRepository.save(cidadeEncontrada);
            return cidadeEncontrada;
    }

    public void deletar(Long cidadeId){
        try {
            Cidade cidadeEncontrada = cidadeRepository.findByIdOrElseThrowException(cidadeId);
            cidadeRepository.delete(cidadeEncontrada);
        } catch (DataIntegrityViolationException e) {
            throw new EntidadeEmUsoException("Cidade com id " + cidadeId + " não pode ser removida pois está sendo utilizado por outra classe");
        }

    }

}
