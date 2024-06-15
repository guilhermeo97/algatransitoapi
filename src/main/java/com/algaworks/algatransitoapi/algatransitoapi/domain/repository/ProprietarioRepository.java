package com.algaworks.algatransitoapi.algatransitoapi.domain.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.algaworks.algatransitoapi.algatransitoapi.domain.model.Proprietario;

@Repository
public interface ProprietarioRepository extends JpaRepository<Proprietario, Long>{
    List<Proprietario> findByNomeContaining(String nome);
    Optional<Proprietario> findByEmail(String email);
}
