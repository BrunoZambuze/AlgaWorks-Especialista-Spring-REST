package com.algaworks.algafood_api.api.controller;

import com.algaworks.algafood_api.domain.exception.CidadeNaoEncontradaException;
import com.algaworks.algafood_api.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood_api.domain.exception.EstadoNaoEncontradoException;
import com.algaworks.algafood_api.domain.model.Cidade;
import com.algaworks.algafood_api.domain.repository.CidadeRepository;
import com.algaworks.algafood_api.domain.service.CadastroCidadeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<?> buscar(@PathVariable Long cidadeId){
        try{
            Cidade cidadeEncontrada = cidadeService.buscar(cidadeId);
            return ResponseEntity.ok(cidadeEncontrada);
        } catch (EntidadeNaoEncontradaException e) {
            if("Cidade".equals(e.getNomeEntidade())){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
            }
            return ResponseEntity.internalServerError().build();
        }
    }

    @PostMapping
    public ResponseEntity<?> adicionar(@RequestBody Cidade cidade){
        try{
            Cidade cidadeCadastrada = cidadeService.salvar(cidade);
            return ResponseEntity.status(HttpStatus.CREATED).body(cidadeCadastrada);
        } catch (EntidadeNaoEncontradaException e){
            if("Estado".equals(e.getNomeEntidade())){
                return ResponseEntity.badRequest().body(e.getMessage());
            } else {
                return ResponseEntity.internalServerError().build();
            }
        }
    }

    @PutMapping("/{cidadeId}")
    public ResponseEntity<?> atualizar(@PathVariable Long cidadeId,
                                       @RequestBody Cidade cidadeAlterar){
        try{
            Cidade cidadeAtualizada = cidadeService.atualizar(cidadeId, cidadeAlterar);
            return ResponseEntity.ok(cidadeAtualizada);
        } catch (EntidadeNaoEncontradaException e) {
            if("Cidade".equals(e.getNomeEntidade())){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
            } else if("Estado".equals(e.getNomeEntidade())){
                return ResponseEntity.badRequest().body(e.getMessage());
            } else{
                return ResponseEntity.internalServerError().build();
            }
        }
    }

    @DeleteMapping("/{cidadeId}")
    public ResponseEntity<?> deletar(@PathVariable Long cidadeId){
        try{
            cidadeService.deletar(cidadeId);
            return ResponseEntity.noContent().build();
        }catch (CidadeNaoEncontradaException e){
            return ResponseEntity.notFound().build();
        }
    }

}
