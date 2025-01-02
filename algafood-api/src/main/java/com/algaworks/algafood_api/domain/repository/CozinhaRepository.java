package com.algaworks.algafood_api.domain.repository;

import com.algaworks.algafood_api.domain.model.Cozinha;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CozinhaRepository extends JpaRepository<Cozinha, Long>, CozinhaRepositoryQueries {

    List<Cozinha> findTodasByNomeContaining(String nomeCozinha);

}
