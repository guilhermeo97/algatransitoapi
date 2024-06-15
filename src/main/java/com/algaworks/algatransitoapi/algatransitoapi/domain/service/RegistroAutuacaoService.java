package com.algaworks.algatransitoapi.algatransitoapi.domain.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.springframework.web.bind.annotation.RequestMapping;

import com.algaworks.algatransitoapi.algatransitoapi.domain.model.Autuacao;
import com.algaworks.algatransitoapi.algatransitoapi.domain.model.Veiculo;
import com.algaworks.algatransitoapi.algatransitoapi.domain.repository.AutuacaoRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
@RequestMapping("/veiculos/{veiculoId}/autuacoes")
public class RegistroAutuacaoService {
    
    private AutuacaoRepository autuacaoRepository;
    private RegistroVeiculoService registroVeiculoService;

    @Transactional
    public Autuacao registrar(Long veiculoId, Autuacao novAutuacao){
        Veiculo veiculo = registroVeiculoService.buscar(veiculoId);
        return veiculo.adicionarAutuacao(novAutuacao);

    }
}
