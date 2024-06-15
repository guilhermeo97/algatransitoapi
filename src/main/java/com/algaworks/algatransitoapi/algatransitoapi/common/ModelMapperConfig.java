package com.algaworks.algatransitoapi.algatransitoapi.common;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class ModelMapperConfig {
    
    //Para o Spring consiga instanciar a classe ModelMapper no controlador
    @Bean
    public ModelMapper modelMapper(){
        //var modelMapper = new ModelMapper();

        // quando não há uma correspondência de nomes dos atributos
        // modelMapper.createTypeMap(Veiculo.class, VeiculoRepresentationModel.class)
        //         .addMappings(mapper -> mapper.map(Veiculo::getPlaca, VeiculoRepresentationModel::setNumeroPlaca));
        
        return new ModelMapper();
    }
}
