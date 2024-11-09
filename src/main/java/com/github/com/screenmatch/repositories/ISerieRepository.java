package com.github.com.screenmatch.repositories;

import com.github.com.screenmatch.models.Serie;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ISerieRepository extends JpaRepository<Serie , Long> {
    Optional<Serie> findByTituloContainingIgnoreCase(String nomeSerie);
}
