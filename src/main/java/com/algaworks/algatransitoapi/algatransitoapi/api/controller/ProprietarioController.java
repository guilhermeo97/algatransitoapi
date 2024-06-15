package com.algaworks.algatransitoapi.algatransitoapi.api.controller;

import java.util.List;

import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algatransitoapi.algatransitoapi.domain.model.Proprietario;
import com.algaworks.algatransitoapi.algatransitoapi.domain.repository.ProprietarioRepository;
import com.algaworks.algatransitoapi.algatransitoapi.domain.service.RegistroProprietarioService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;


@RestController
@AllArgsConstructor
@RequestMapping("/proprietarios")
public class ProprietarioController {
    
    private final ProprietarioRepository proprietarioRepository;
    private final RegistroProprietarioService registroProprietarioService;


    @GetMapping
    public List<Proprietario> listar(){
        return proprietarioRepository.findAll();
    }

    
    @GetMapping("/{id}")
    public ResponseEntity<Proprietario> listarProprietario(@PathVariable Long id){
        return proprietarioRepository.findById(id).map(proprietario -> ResponseEntity.ok(proprietario)).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Proprietario adicionar(@Valid @RequestBody Proprietario proprietario){
        return registroProprietarioService.salvar(proprietario);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Proprietario> atualizar(@PathVariable Long id, @Valid @RequestBody Proprietario proprietario ){
        if(!proprietarioRepository.existsById(id)){
            return ResponseEntity.notFound().build();
        }
        proprietario.setId(id);
        Proprietario proprietarioAtualizado = registroProprietarioService.salvar(proprietario);
        return ResponseEntity.ok(proprietarioAtualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id){
        if(!proprietarioRepository.existsById(id)){
            return ResponseEntity.notFound().build();
        }
        registroProprietarioService.excluir(id);
        return ResponseEntity.noContent().build();
    }

}
