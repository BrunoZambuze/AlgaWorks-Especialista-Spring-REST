package com.algaworks.algafood_api.domain.repository;

import com.algaworks.algafood_api.domain.model.Restaurante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RestauranteRepository extends JpaRepository<Restaurante, Long> {


}
