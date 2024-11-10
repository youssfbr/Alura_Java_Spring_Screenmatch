package com.github.com.screenmatch.repositories;

import com.github.com.screenmatch.models.Episodio;
import com.github.com.screenmatch.models.Serie;
import com.github.com.screenmatch.models.enums.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ISerieRepository extends JpaRepository<Serie , Long> {

    Optional<Serie> findByTituloContainingIgnoreCase(String nomeSerie);

    List<Serie> findByAtoresContainingIgnoreCase(String nomeAtor);
    List<Serie> findByAtoresContainingIgnoreCaseAndAvaliacaoGreaterThanEqual(String nomeAtor, Double avaliacao);
    List<Serie> findTop5ByOrderByAvaliacaoDesc();
    List<Serie> findByGenero(Categoria categoria);
    List<Serie> findByTotalTemporadasLessThanEqualAndAvaliacaoGreaterThanEqual(int totalTemporadas, double avaliacao);

    @Query(nativeQuery = true, value = """
            SELECT * FROM tb_series s
            WHERE s.total_temporadas <= :totalTemporadas
            AND s.avaliacao >= :avaliacao""")
    List<Serie> seriesPorTemporadaEAvaliacaoSQL(int totalTemporadas, double avaliacao);

    @Query("""
            SELECT s FROM Serie s
            WHERE s.totalTemporadas <= :totalTemporadas
            AND s.avaliacao >= :avaliacao""")
    List<Serie> seriesPorTemporadaEAvaliacaoJPQL(int totalTemporadas, double avaliacao);

    @Query("""
            SELECT e FROM Serie s
            JOIN s.episodios e
            WHERE e.titulo 
            ILIKE %:trechoEpisodio% 
            """)
    List<Episodio> episodiosPorTrecho(String trechoEpisodio);
}
