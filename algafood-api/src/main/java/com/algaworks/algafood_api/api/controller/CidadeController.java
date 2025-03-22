package com.algaworks.algafood_api.api.controller;

import com.algaworks.algafood_api.api.assembler.CidadeInputDisassembler;
import com.algaworks.algafood_api.api.assembler.CidadeModelAssembler;
import com.algaworks.algafood_api.api.model.representationmodel.CidadeModel;
import com.algaworks.algafood_api.api.model.representationmodel.input.CidadeInput;
import com.algaworks.algafood_api.domain.exception.EstadoNaoEncontradoException;
import com.algaworks.algafood_api.domain.exception.NegocioException;
import com.algaworks.algafood_api.domain.model.Cidade;
import com.algaworks.algafood_api.domain.repository.CidadeRepository;
import com.algaworks.algafood_api.domain.service.CadastroCidadeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/cidades")
public class CidadeController {

    @Autowired
    private CidadeRepository cidadeRepository;

    @Autowired
    private CadastroCidadeService cidadeService;

    @Autowired
    private CidadeModelAssembler cidadeModelAssembler;

    @Autowired
    private CidadeInputDisassembler cidadeInputDisassembler;

    @GetMapping
    public List<CidadeModel> listar(){
        return cidadeModelAssembler.toCollectionModel(cidadeRepository.findAll());
    }

    @GetMapping("/{cidadeId}")
    public CidadeModel buscar(@PathVariable Long cidadeId){
        return cidadeModelAssembler.toModel(cidadeRepository.findByIdOrElseThrowException(cidadeId));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CidadeModel adicionar(@RequestBody @Valid CidadeInput cidadeInput){
        Cidade cidade = cidadeInputDisassembler.toDomainObject(cidadeInput);
        try {
            return cidadeModelAssembler.toModel(cidadeService.salvar(cidade));
        } catch (EstadoNaoEncontradoException e) {
            throw new NegocioException(e.getMessage());
        }
    }

    @PutMapping("/{cidadeId}")
    public CidadeModel atualizar(@PathVariable Long cidadeId,
                            @RequestBody @Valid CidadeInput cidadeInput){
        Cidade cidade = cidadeRepository.findByIdOrElseThrowException(cidadeId);
        cidadeInputDisassembler.toCopyDomain(cidadeInput, cidade);
        try{
            return cidadeModelAssembler.toModel(cidadeService.atualizar(cidade));
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