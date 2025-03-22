package com.algaworks.algafood_api.api.controller;

import com.algaworks.algafood_api.api.assembler.EstadoInputDisassembler;
import com.algaworks.algafood_api.api.assembler.EstadoModelAssembler;
import com.algaworks.algafood_api.api.model.representationmodel.EstadoModel;
import com.algaworks.algafood_api.api.model.representationmodel.input.EstadoInput;
import com.algaworks.algafood_api.domain.model.Estado;
import com.algaworks.algafood_api.domain.repository.EstadoRepository;
import com.algaworks.algafood_api.domain.service.CadastroEstadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/estados")
public class EstadoController {

    @Autowired
    private EstadoRepository estadoRepository;

    @Autowired
    private CadastroEstadoService estadoService;

    @Autowired
    private EstadoModelAssembler estadoModelAssembler;

    @Autowired
    private EstadoInputDisassembler estadoInputDisassembler;

    @GetMapping
    public List<EstadoModel> listar(){
        return estadoModelAssembler.toCollectionModel(estadoRepository.findAll());
    }

    @GetMapping("/{estadoId}")
    public EstadoModel buscar(@PathVariable Long estadoId){
        return estadoModelAssembler.toModel(estadoRepository.findByIdOrElseThrowException(estadoId));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EstadoModel adicionar(@RequestBody @Valid EstadoInput estadoInput){
        Estado estado = estadoInputDisassembler.toDomainObject(estadoInput);
        return estadoModelAssembler.toModel(estadoService.adicionar(estado));
    }

    @PutMapping("/{estadoId}")
    public EstadoModel atualizar(@PathVariable Long estadoId,
                            @RequestBody @Valid EstadoInput estadoInput){
        Estado estado = estadoRepository.findByIdOrElseThrowException(estadoId);
        estadoInputDisassembler.copyToDomainObject(estadoInput, estado);

        return estadoModelAssembler.toModel(estadoService.atualizar(estado));
    }

    @DeleteMapping("/{estadoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletar(@PathVariable Long estadoId){
        estadoService.remover(estadoId);
    }

}
