package com.algaworks.algatransitoapi.algatransitoapi.api.controller;

import java.util.List;

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
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algatransitoapi.algatransitoapi.api.assembler.VeiculoAssembler;
import com.algaworks.algatransitoapi.algatransitoapi.api.model.VeiculoRepresentationModel;
import com.algaworks.algatransitoapi.algatransitoapi.api.model.input.VeiculoInput;
import com.algaworks.algatransitoapi.algatransitoapi.domain.model.Veiculo;
import com.algaworks.algatransitoapi.algatransitoapi.domain.repository.VeiculoRepository;
import com.algaworks.algatransitoapi.algatransitoapi.domain.service.ApreensaoVeiculoService;
import com.algaworks.algatransitoapi.algatransitoapi.domain.service.RegistroVeiculoService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/veiculos")
@AllArgsConstructor
public class VeiculoController {
    
    private final VeiculoRepository veiculoRepository;
    private final RegistroVeiculoService registroVeiculoService;
    private final VeiculoAssembler veiculoAssembler;
    private final ApreensaoVeiculoService apreensaoVeiculoService;


    @GetMapping
    public List<VeiculoRepresentationModel> listar(){
        return veiculoAssembler.toCollectionModel(veiculoRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<VeiculoRepresentationModel> buscar(@PathVariable Long id){
        //Model mapper faz a trasnsformações de um dto para uma classe
        return veiculoRepository.findById(id).map(veiculoAssembler::toModel).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public VeiculoRepresentationModel adicionar(@RequestBody @Valid VeiculoInput veiculoInput){
        Veiculo novoVeiculo = veiculoAssembler.toEntity(veiculoInput);
        Veiculo veiculoCadastrado = registroVeiculoService.cadastrar(novoVeiculo);
        return veiculoAssembler.toModel(veiculoCadastrado);
    }

    @PutMapping("/{id}/apreensao")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void apreender(Long id){
        apreensaoVeiculoService.apreenderVeiculo(id);
    }

    @DeleteMapping("/{id}/apreensao")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removerApreender(@PathVariable Long id){
        apreensaoVeiculoService.removerApreensao(id);
    }

}

