package com.algaworks.algafood_api.api.controller;

import com.algaworks.algafood_api.api.assembler.CozinhaAssembler;
import com.algaworks.algafood_api.api.assembler.CozinhaInputDisassembler;
import com.algaworks.algafood_api.api.model.representationmodel.CozinhaModel;
import com.algaworks.algafood_api.api.model.representationmodel.input.CozinhaInput;
import com.algaworks.algafood_api.domain.model.Cozinha;
import com.algaworks.algafood_api.domain.repository.CozinhaRepository;
import com.algaworks.algafood_api.domain.service.CadastroCozinhaService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/cozinhas")
public class CozinhaController {

    @Autowired
    private CozinhaRepository cozinhaRepository;

    @Autowired
    private CadastroCozinhaService cozinhaService;

    @Autowired
    private CozinhaAssembler cozinhaAssembler;

    @Autowired
    private CozinhaInputDisassembler cozinhaInputDisassembler;

    @GetMapping
    public List<CozinhaModel> listar(){
        return cozinhaAssembler.toCollectionModel(cozinhaRepository.findAll());
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{cozinhaId}")
    public CozinhaModel buscar(@PathVariable Long cozinhaId){
        return cozinhaAssembler.toModel(cozinhaService.buscarOuFalhar(cozinhaId));
    }

    @Transactional
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public CozinhaModel adicionar(@RequestBody @Valid CozinhaInput cozinhaInput){
        Cozinha cozinha = cozinhaInputDisassembler.toObjectDomain(cozinhaInput);
        return cozinhaAssembler.toModel(cozinhaService.salvar(cozinha));
    }

    @Transactional
    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/{cozinhaId}")
    public CozinhaModel atualizar(@PathVariable Long cozinhaId,
                             @RequestBody @Valid CozinhaInput cozinhaInput){
        Cozinha cozinha = cozinhaService.buscarOuFalhar(cozinhaId);
        cozinhaInputDisassembler.toCopyDomain(cozinhaInput, cozinha);
        return cozinhaAssembler.toModel(cozinhaService.salvar(cozinha));
    }

    @Transactional
    @DeleteMapping("/{cozinhaId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletar(@PathVariable Long cozinhaId){
        cozinhaService.excluir(cozinhaId);
    }

}
