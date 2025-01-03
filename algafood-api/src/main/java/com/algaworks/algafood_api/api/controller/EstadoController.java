package com.algaworks.algafood_api.api.controller;

import com.algaworks.algafood_api.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood_api.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood_api.domain.model.Estado;
import com.algaworks.algafood_api.domain.repository.EstadoRepository;
import com.algaworks.algafood_api.domain.service.CadastroEstadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/estados")
public class EstadoController {

    @Autowired
    private EstadoRepository estadoRepository;

    @Autowired
    private CadastroEstadoService estadoService;

    @GetMapping
    public List<Estado> listar(){
        return estadoRepository.findAll();
    }

    @GetMapping("/{estadoId}")
    public ResponseEntity<Estado> buscar(@PathVariable Long estadoId){
        Optional<Estado> estadoEncontrado = estadoRepository.findById(estadoId);
        return estadoEncontrado.isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.ok(estadoEncontrado.get());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Estado adicionar(@RequestBody Estado estado){
        return estadoService.adicionar(estado);
    }

    @PutMapping("/{estadoId}")
    public ResponseEntity<?> atualizar(@PathVariable Long estadoId,
                                       @RequestBody Estado estadoAlterado){
        try{
            Estado estadoAtualizado = estadoService.atualizar(estadoId, estadoAlterado);
            return ResponseEntity.ok(estadoAtualizado);
        } catch (EntidadeNaoEncontradaException e) {
            if("Estado".equals(e.getNomeEntidade())){
                return ResponseEntity.notFound().build();
            } else{
                return ResponseEntity.internalServerError().build();
            }
        }
    }

    @DeleteMapping("/{estadoId}")
    public ResponseEntity<?> deletar(@PathVariable Long estadoId){
        try{
            estadoService.remover(estadoId);
            return ResponseEntity.noContent().build();
        } catch (EntidadeNaoEncontradaException e){
            if("Estado".equals(e.getNomeEntidade())){
                return ResponseEntity.notFound().build();
            }
            else{
                return ResponseEntity.internalServerError().build();
            }
        } catch (EntidadeEmUsoException e){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

}
