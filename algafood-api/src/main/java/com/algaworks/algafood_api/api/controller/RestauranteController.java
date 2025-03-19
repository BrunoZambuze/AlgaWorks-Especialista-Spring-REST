package com.algaworks.algafood_api.api.controller;
import com.algaworks.algafood_api.api.assembler.RestauranteInputDisassembler;
import com.algaworks.algafood_api.api.assembler.RestauranteModelAssembler;
import com.algaworks.algafood_api.api.model.representationmodel.RestauranteModel;
import com.algaworks.algafood_api.api.model.representationmodel.input.RestauranteInput;
import com.algaworks.algafood_api.domain.exception.CozinhaNaoEncontradaException;
import com.algaworks.algafood_api.domain.exception.NegocioException;
import com.algaworks.algafood_api.domain.model.Restaurante;
import com.algaworks.algafood_api.domain.repository.RestauranteRepository;
import com.algaworks.algafood_api.domain.service.CadastroRestauranteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/restaurantes")
public class RestauranteController {

    @Autowired
    private RestauranteRepository restauranteRepository;

    @Autowired
    private CadastroRestauranteService restauranteService;

    @Autowired
    private RestauranteModelAssembler restauranteModelAssembler;

    @Autowired
    private RestauranteInputDisassembler restauranteInputDisassembler;

    @GetMapping
    public List<RestauranteModel> listar(){
        return restauranteModelAssembler.toCollectionModel(restauranteRepository.findAll());
    }

    @GetMapping("/{restauranteId}")
    @ResponseStatus(HttpStatus.OK)
    public RestauranteModel buscar(@PathVariable Long restauranteId){
        Restaurante restaurante = restauranteService.buscarOuFalhar(restauranteId);

        return restauranteModelAssembler.toModel(restaurante);
    }

    @Transactional
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RestauranteModel adicionar(@RequestBody @Valid RestauranteInput restauranteInput){
        try{
            Restaurante restaurante = restauranteInputDisassembler.toDomainObject(restauranteInput);
            return restauranteModelAssembler.toModel(restauranteService.salvar(restaurante));
        }catch (CozinhaNaoEncontradaException e){
            throw new NegocioException(e.getMessage());
        }
    }

    @Transactional
    @PutMapping("/{restauranteId}")
    @ResponseStatus(HttpStatus.OK)
    public RestauranteModel atualizar(@PathVariable Long restauranteId,
                                 @RequestBody @Valid RestauranteInput restauranteInput){
        try{
            Restaurante restaurante = restauranteInputDisassembler.toDomainObject(restauranteInput);
            return restauranteModelAssembler.toModel(restauranteService.atualizar(restauranteId, restaurante));
        }catch (CozinhaNaoEncontradaException e){
            throw new NegocioException(e.getMessage());
        }
    }

    @Transactional
    @PatchMapping("/{restauranteId}")
    @ResponseStatus(HttpStatus.OK)
    public RestauranteModel atualizarParcial(@PathVariable Long restauranteId,
                                        @RequestBody Map<String, Object> campos,
                                        HttpServletRequest request){
        return restauranteModelAssembler.toModel(restauranteService.atualizarParcial(restauranteId, campos, request));
    }

}