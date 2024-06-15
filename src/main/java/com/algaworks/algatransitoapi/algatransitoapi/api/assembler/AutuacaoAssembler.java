package com.algaworks.algatransitoapi.algatransitoapi.api.assembler;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.algaworks.algatransitoapi.algatransitoapi.api.model.AutuacaoModel;
import com.algaworks.algatransitoapi.algatransitoapi.api.model.input.AutuacaoInput;
import com.algaworks.algatransitoapi.algatransitoapi.domain.model.Autuacao;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class AutuacaoAssembler {
    private ModelMapper modelMapper;

    public Autuacao toEntity(AutuacaoInput autuacaoInput){
        return modelMapper.map(autuacaoInput, Autuacao.class);
    }
    
    public AutuacaoModel toModel(Autuacao autuacao){
        return modelMapper.map(autuacao, AutuacaoModel.class);
    }

    public List<AutuacaoModel> toCollectionModel(List<Autuacao> autuacoes){
        return autuacoes.stream()
        .map(this::toModel)
        .toList();
    }
}
