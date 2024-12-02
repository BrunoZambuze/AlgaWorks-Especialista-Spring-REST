package com.algaworks.algafood_api.domain.service;

import com.algaworks.algafood_api.domain.exception.CozinhaNaoEncontradaException;
import com.algaworks.algafood_api.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood_api.domain.model.Cozinha;
import com.algaworks.algafood_api.domain.repository.CozinhaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
public class CadastroCozinhaService {

    @Autowired
    private CozinhaRepository cozinhaRepository;

    public Cozinha salvar(Cozinha cozinha){
        return cozinhaRepository.save(cozinha);
    }

    public void excluir(Long cozinhaId){
        try{
            if(!cozinhaRepository.existsById(cozinhaId)){
                throw new CozinhaNaoEncontradaException(String.format("Não existe um cadastro de cozinha com o código %d", cozinhaId));
            }
            cozinhaRepository.deleteById(cozinhaId);

        }catch (DataIntegrityViolationException e){
            throw new EntidadeEmUsoException(String.format("Cozinha de código %d não pode ser removida, pois está em uso", cozinhaId));

        }
    }

}
