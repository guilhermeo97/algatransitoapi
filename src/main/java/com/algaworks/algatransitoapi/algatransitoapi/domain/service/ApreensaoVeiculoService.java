package com.algaworks.algatransitoapi.algatransitoapi.domain.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.algaworks.algatransitoapi.algatransitoapi.domain.model.StatusVeiculo;
import com.algaworks.algatransitoapi.algatransitoapi.domain.model.Veiculo;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ApreensaoVeiculoService {
    
    private final RegistroVeiculoService registroVeiculoService;

    @Transactional
    public void apreenderVeiculo(Long id){
        Veiculo veiculo = registroVeiculoService.buscar(id);

        veiculo.apreender();
    }

    @Transactional
    public void removerApreensao(Long id){
        Veiculo veiculo = registroVeiculoService.buscar(id);
        veiculo.removerApreensao();
    }
}
