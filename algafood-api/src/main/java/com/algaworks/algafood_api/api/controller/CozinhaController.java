package com.algaworks.algafood_api.api.controller;


import com.algaworks.algafood_api.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood_api.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood_api.domain.model.Cozinha;
import com.algaworks.algafood_api.domain.repository.CozinhaRepository;
import com.algaworks.algafood_api.domain.service.CadastroCozinhaService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/cozinhas")
public class CozinhaController {

    @Autowired
    private CozinhaRepository cozinhaRepository;

    @Autowired
    private CadastroCozinhaService cozinhaService;

    @GetMapping
    public List<Cozinha> listar(){
        return cozinhaRepository.findAll();
    }

    @GetMapping("/{cozinhaId}")
    public ResponseEntity<Cozinha> buscar(@PathVariable Long cozinhaId){
        Optional<Cozinha> cozinhaEncontrada = cozinhaRepository.findById(cozinhaId);
        return cozinhaEncontrada.isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.ok(cozinhaEncontrada.get());
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public Cozinha adicionar(@RequestBody Cozinha cozinha){
        return cozinhaService.salvar(cozinha);
    }

    @PutMapping("/{cozinhaId}")
    public ResponseEntity<Cozinha> atualizar(@PathVariable Long cozinhaId,
                                             @RequestBody Cozinha cozinhaAlterar){
        Optional<Cozinha> cozinhaEncontrada = cozinhaRepository.findById(cozinhaId);
        if(cozinhaEncontrada.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        BeanUtils.copyProperties(cozinhaAlterar, cozinhaEncontrada.get(), "id");
        Cozinha cozinhaSalva = cozinhaService.salvar(cozinhaEncontrada.get());
        return ResponseEntity.ok(cozinhaSalva);
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
