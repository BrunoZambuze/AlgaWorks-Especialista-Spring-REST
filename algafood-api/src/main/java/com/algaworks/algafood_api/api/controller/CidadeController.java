package com.algaworks.algafood_api.api.controller;

import com.algaworks.algafood_api.api.exceptionhandler.Problema;
import com.algaworks.algafood_api.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood_api.domain.exception.EstadoNaoEncontradoException;
import com.algaworks.algafood_api.domain.exception.NegocioException;
import com.algaworks.algafood_api.domain.model.Cidade;
import com.algaworks.algafood_api.domain.repository.CidadeRepository;
import com.algaworks.algafood_api.domain.service.CadastroCidadeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/cidades")
public class CidadeController {

    @Autowired
    private CidadeRepository cidadeRepository;

    @Autowired
    private CadastroCidadeService cidadeService;

    @GetMapping
    public List<Cidade> listar(){
        return cidadeRepository.findAll();
    }

    @GetMapping("/{cidadeId}")
    public Cidade buscar(@PathVariable Long cidadeId){
        return cidadeRepository.findByIdOrElseThrowException(cidadeId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Cidade adicionar(@RequestBody Cidade cidade){
        try {
            return cidadeService.salvar(cidade);
        } catch (EstadoNaoEncontradoException e) {
            throw new NegocioException(e.getMessage());
        }
    }

    @PutMapping("/{cidadeId}")
    public Cidade atualizar(@PathVariable Long cidadeId,
                            @RequestBody Cidade cidadeAlterar){
        try{
            return cidadeService.atualizar(cidadeId, cidadeAlterar);
        } catch (EstadoNaoEncontradoException e) {
            throw new NegocioException(e.getMessage());
        }
    }

    @DeleteMapping("/{cidadeId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletar(@PathVariable Long cidadeId){
        cidadeService.deletar(cidadeId);
    }

}
