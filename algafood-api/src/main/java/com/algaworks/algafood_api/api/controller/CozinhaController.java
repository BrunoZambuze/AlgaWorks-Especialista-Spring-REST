package com.algaworks.algafood_api.api.controller;


import com.algaworks.algafood_api.api.model.CozinhasXmlWrapper;
import com.algaworks.algafood_api.domain.model.Cozinha;
import com.algaworks.algafood_api.domain.repository.CozinhaRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/cozinhas")
public class CozinhaController {

    @Autowired
    private CozinhaRepository cozinhaRepository;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Cozinha> listar(){
        return cozinhaRepository.listar();
    }

    @GetMapping(produces = MediaType.APPLICATION_XML_VALUE)
    public CozinhasXmlWrapper listarXml(){
        return new CozinhasXmlWrapper(cozinhaRepository.listar());
    }

    @GetMapping("/{cozinhaId}")
    public ResponseEntity<Cozinha> buscar(@PathVariable Long cozinhaId){
        Cozinha cozinhaEncontrada = cozinhaRepository.buscar(cozinhaId);
        return cozinhaEncontrada == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(cozinhaEncontrada);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public Cozinha adicionar(@RequestBody Cozinha cozinha){
        return cozinhaRepository.salvar(cozinha);
    }

    @PutMapping("/{cozinhaId}")
    public ResponseEntity<Cozinha> atualizar(@PathVariable Long cozinhaId,
                                             @RequestBody Cozinha cozinhaAlterar){
        Cozinha cozinhaEncontrada = cozinhaRepository.buscar(cozinhaId);
        if(cozinhaEncontrada == null){
            return ResponseEntity.notFound().build();
        }
        BeanUtils.copyProperties(cozinhaAlterar, cozinhaEncontrada, "id");
        cozinhaEncontrada = cozinhaRepository.salvar(cozinhaEncontrada);
        return ResponseEntity.ok(cozinhaEncontrada);
    }

    @DeleteMapping("/{cozinhaId}")
    public ResponseEntity<Void> deletar(@PathVariable Long cozinhaId){
        Cozinha cozinhaEncontrada = cozinhaRepository.buscar(cozinhaId);
        try {
            if (cozinhaEncontrada == null) {
                return ResponseEntity.notFound().build();
            }
            cozinhaRepository.remover(cozinhaEncontrada);
            return ResponseEntity.noContent().build();
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

}
