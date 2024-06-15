package com.algaworks.algatransitoapi.algatransitoapi.domain.service;

import java.time.OffsetDateTime;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.algaworks.algatransitoapi.algatransitoapi.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algatransitoapi.algatransitoapi.domain.exception.NegocioException;
import com.algaworks.algatransitoapi.algatransitoapi.domain.model.Proprietario;
import com.algaworks.algatransitoapi.algatransitoapi.domain.model.StatusVeiculo;
import com.algaworks.algatransitoapi.algatransitoapi.domain.model.Veiculo;
import com.algaworks.algatransitoapi.algatransitoapi.domain.repository.VeiculoRepository;

import lombok.AllArgsConstructor;



@Service
@AllArgsConstructor
public class RegistroVeiculoService {
    
    private VeiculoRepository veiculoRepository;
    private RegistroProprietarioService registroProprietarioService;

    public Veiculo buscar(Long veiculoId){
        return veiculoRepository.findById(veiculoId).orElseThrow(() -> new EntidadeNaoEncontradaException("Veículo não encontrado"));
    }

    @Transactional
    public Veiculo cadastrar(Veiculo novoVeiculo){
        if(novoVeiculo.getId() != null){
            throw new NegocioException("Veículo a ser cadastrado não pode possuir ID");
        }
        
        boolean placaEmUso = veiculoRepository.findByPlaca(novoVeiculo.getPlaca()).filter(veiculo -> !veiculo.equals(novoVeiculo)).isPresent();
        
        if(placaEmUso){
            throw new NegocioException("Já existem um veículo cadastrado com essa placa ");
        }
        
        Proprietario proprietario = registroProprietarioService.buscar(novoVeiculo.getProprietario().getId());

        novoVeiculo.setProprietario(proprietario);
        novoVeiculo.setStatus(StatusVeiculo.REGULAR);
        novoVeiculo.setDataCadastro(OffsetDateTime.now());
        
        return veiculoRepository.save(novoVeiculo);
    }
}
