package com.algaworks.algatransitoapi.algatransitoapi.domain.model;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

import com.algaworks.algatransitoapi.algatransitoapi.domain.exception.NegocioException;
import com.algaworks.algatransitoapi.algatransitoapi.domain.validation.ValidationGroups;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.groups.ConvertGroup;
import jakarta.validation.groups.Default;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;


@Entity
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Veiculo {
    
    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotNull
    @ManyToOne
    //Vai na classe e valida os atributos
    @Valid
    //Converte o grupo de validação padrão para um grupo que foi criado
    @ConvertGroup(from = Default.class, to = ValidationGroups.ProprietarioId.class)
    //@JoinColumn(name = "proprietario_id") - não é necessário se os nmes forem parecidos
    private Proprietario proprietario;

    @NotNull
    private String marca;

    @NotBlank
    private String modelo;

    @NotBlank
    @Pattern(regexp = "[A-Z]{3}[0-9]{1}[0-9A-Z]{1}[0-9]{2}")
    //XXX0000
    //XXX0X00
    private String placa;
    

    //bloqueia o cadastro do atributo no banco de dados
    @JsonProperty(access = Access.READ_ONLY)
    //diz o que queremos guardar no banco de dados
    @Enumerated(EnumType.STRING)
    private StatusVeiculo status;

    @JsonProperty(access = Access.READ_ONLY)
    private OffsetDateTime dataCadastro;

    @JsonProperty(access = Access.READ_ONLY)
    private OffsetDateTime dataApreensao;

    @OneToMany(mappedBy = "veiculo", cascade = CascadeType.ALL)
    private List<Autuacao> autuacoes = new ArrayList<>();

    public Autuacao adicionarAutuacao(Autuacao autuacao){
        autuacao.setDataOcorrencia(OffsetDateTime.now());
        autuacao.setVeiculo(this);
        getAutuacoes().add(autuacao);
        return autuacao;
    }

    public void apreender(){
        if(estaApreendido()){
            throw new NegocioException("Veículo já encontra-se ampreendido");
        }
        setStatus(StatusVeiculo.APREENDIDO);
        setDataApreensao(OffsetDateTime.now());
    }
        
    private boolean estaApreendido(){
        return StatusVeiculo.APREENDIDO.equals(getStatus());
    }

    public void removerApreensao(){
        if(naoEstaApreendido()){
            throw new NegocioException("Veículo não encontra-se ampreendido");
        }
    }

    private boolean naoEstaApreendido(){
        return !estaApreendido();
    }
}
