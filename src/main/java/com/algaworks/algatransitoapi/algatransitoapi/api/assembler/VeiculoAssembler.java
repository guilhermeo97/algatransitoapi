package com.algaworks.algatransitoapi.algatransitoapi.api.assembler;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.algaworks.algatransitoapi.algatransitoapi.api.model.VeiculoRepresentationModel;
import com.algaworks.algatransitoapi.algatransitoapi.api.model.input.VeiculoInput;
import com.algaworks.algatransitoapi.algatransitoapi.domain.model.Veiculo;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class VeiculoAssembler {
    private ModelMapper modelMapper;

    public VeiculoRepresentationModel toModel(Veiculo veiculo){
        return modelMapper.map(veiculo, VeiculoRepresentationModel.class);
    }

    public List<VeiculoRepresentationModel> toCollectionModel(List<Veiculo> veiculos){
        return  veiculos.stream()
            .map(this::toModel)
            .toList();
    }

    public Veiculo toEntity(VeiculoInput veiculoInput){
        return modelMapper.map(veiculoInput, Veiculo.class);
    }
}
