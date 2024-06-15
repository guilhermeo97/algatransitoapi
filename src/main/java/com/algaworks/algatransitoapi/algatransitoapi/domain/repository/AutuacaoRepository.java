package com.algaworks.algatransitoapi.algatransitoapi.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.algaworks.algatransitoapi.algatransitoapi.domain.model.Autuacao;

public interface AutuacaoRepository extends JpaRepository<Autuacao, Long> {
}
