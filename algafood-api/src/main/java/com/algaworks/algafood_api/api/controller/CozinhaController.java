package com.algaworks.algafood_api.api.controller;


import com.algaworks.algafood_api.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood_api.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood_api.domain.model.Cozinha;
import com.algaworks.algafood_api.domain.repository.CozinhaRepository;
import com.algaworks.algafood_api.domain.service.CadastroCozinhaService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cozinhas")
public class CozinhaController {

    @Autowired
    private CozinhaRepository cozinhaRepository;

    @Autowired
    private CadastroCozinhaService cozinhaService;

    @GetMapping
    public List<Cozinha> listar(){
        return cozinhaRepository.listar();
    }

    @GetMapping("/{cozinhaId}")
    public ResponseEntity<Cozinha> buscar(@PathVariable Long cozinhaId){
        Cozinha cozinhaEncontrada = cozinhaRepository.buscar(cozinhaId);
        return cozinhaEncontrada == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(cozinhaEncontrada);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public Cozinha adicionar(@RequestBody Cozinha cozinha){
        return cozinhaService.salvar(cozinha);
    }

    @PutMapping("/{cozinhaId}")
    public ResponseEntity<Cozinha> atualizar(@PathVariable Long cozinhaId,
                                             @RequestBody Cozinha cozinhaAlterar){
        Cozinha cozinhaEncontrada = cozinhaRepository.buscar(cozinhaId);
        if(cozinhaEncontrada == null){
            return ResponseEntity.notFound().build();
        }
        BeanUtils.copyProperties(cozinhaAlterar, cozinhaEncontrada, "id");
        cozinhaEncontrada = cozinhaService.salvar(cozinhaEncontrada);
        return ResponseEntity.ok(cozinhaEncontrada);
    }

    @DeleteMapping("/{cozinhaId}")
    public ResponseEntity<Void> deletar(@PathVariable Long cozinhaId){
        try{
            cozinhaService.excluir(cozinhaId);
            return ResponseEntity.noContent().build();

        }catch (EntidadeEmUsoException e){
            return ResponseEntity.status(HttpStatus.CONFLICT).build();

        } catch (EntidadeNaoEncontradaException e){
            if("Cozinha".equals(e.getNomeEntidade())){
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.internalServerError().build();
        }
    }

}
