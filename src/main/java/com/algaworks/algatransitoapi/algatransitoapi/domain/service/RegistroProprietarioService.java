package com.algaworks.algatransitoapi.algatransitoapi.domain.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.algaworks.algatransitoapi.algatransitoapi.domain.exception.NegocioException;
import com.algaworks.algatransitoapi.algatransitoapi.domain.model.Proprietario;
import com.algaworks.algatransitoapi.algatransitoapi.domain.repository.ProprietarioRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class RegistroProprietarioService {
    
    private final ProprietarioRepository proprietarioRepository;

    @Transactional
    public Proprietario salvar(Proprietario proprietario){
        boolean emailEmUso = proprietarioRepository.findByEmail(proprietario.getEmail()).filter(p -> !p.equals(proprietario)).isPresent();

        if(emailEmUso){
            throw new NegocioException("E-mail informado já existe!");
        }

        return proprietarioRepository.save(proprietario);
    }

    @Transactional
    public void excluir(Long id){
        proprietarioRepository.deleteById(id);
    }

    public Proprietario buscar(Long id){
        return proprietarioRepository.findById(id).orElseThrow(() -> new NegocioException("Proprietário não encontrado"));
    }
}
