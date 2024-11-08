package com.github.com.screenmatch.repositories;

import com.github.com.screenmatch.models.Serie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ISerieRepository extends JpaRepository<Serie , Long> {
}
