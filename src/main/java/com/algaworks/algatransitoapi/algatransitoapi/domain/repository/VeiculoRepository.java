package com.algaworks.algatransitoapi.algatransitoapi.domain.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.algaworks.algatransitoapi.algatransitoapi.domain.model.Veiculo;

@Repository
public interface VeiculoRepository extends JpaRepository<Veiculo, Long>{
    public Optional<Veiculo> findByPlaca(String placa);
}
