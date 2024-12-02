package com.algaworks.algafood_api.api.controller;

import com.algaworks.algafood_api.domain.exception.CozinhaNaoEncontradaException;
import com.algaworks.algafood_api.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood_api.domain.model.Restaurante;
import com.algaworks.algafood_api.domain.repository.RestauranteRepository;
import com.algaworks.algafood_api.domain.service.CadastroRestauranteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/restaurantes")
public class RestauranteController {

    @Autowired
    private RestauranteRepository restauranteRepository;

    @Autowired
    private CadastroRestauranteService restauranteService;

    @GetMapping
    public List<Restaurante> listar(){
        return restauranteRepository.findAll();
    }

    @GetMapping("/{restauranteId}")
    public ResponseEntity<Restaurante> buscar(@PathVariable Long restauranteId){
        Optional<Restaurante> restauranteEncontrado = restauranteRepository.findById(restauranteId);
        return restauranteEncontrado.isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.ok(restauranteEncontrado.get());
    }

    @PostMapping
    public ResponseEntity<?> adicionar(@RequestBody Restaurante restaurante){
        try {
            Restaurante restauranteCadastrado = restauranteService.salvar(restaurante);
            return ResponseEntity.status(HttpStatus.CREATED).body(restauranteCadastrado);
        } catch (EntidadeNaoEncontradaException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{restauranteId}")
    public ResponseEntity<?> atualizar(@PathVariable Long restauranteId,
                                       @RequestBody Restaurante restaurante){
        try{
            Restaurante restauranteAlterado = restauranteService.atualizar(restauranteId, restaurante);
            return ResponseEntity.ok(restauranteAlterado);

        } catch (EntidadeNaoEncontradaException e) {
            if("Restaurante".equals(e.getNomeEntidade())){
                return ResponseEntity.notFound().build();
            } else if("Cozinha".equals(e.getNomeEntidade())){
                return ResponseEntity.badRequest().body(e.getMessage());
            } else{
                return ResponseEntity.internalServerError().build();
            }
        }
    }

    @PatchMapping("/{restauranteId}")
    public ResponseEntity<?> atualizarParcial(@PathVariable Long restauranteId,
                                              @RequestBody Map<String, Object> campos){
        try{
            restauranteService.atualizarParcial(restauranteId, campos);
            return ResponseEntity.ok().build();
        } catch (EntidadeNaoEncontradaException e){
            if("Restaurante".equals(e.getNomeEntidade())){
                return ResponseEntity.notFound().build();
            } else if("Cozinha".equals(e.getNomeEntidade())){
                return ResponseEntity.badRequest().body(e.getMessage());
            } else {
                return ResponseEntity.internalServerError().build();
            }
        }
    }

}