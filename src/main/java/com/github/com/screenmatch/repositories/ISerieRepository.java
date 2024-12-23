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

    //    List<Serie> findTop5ByOrderByEpisodiosDataLancamentoDesc();

    @Query("SELECT s FROM Serie s " +
            "JOIN s.episodios e " +
            "GROUP BY s " +
            "ORDER BY MAX(e.dataLancamento) DESC LIMIT 5")
    List<Serie> encontrarEpisodiosMaisRecentes();

    // Poderia usar findByGenero(categoria)
    @Query("SELECT s FROM Serie s " +
            "WHERE s.genero = :categoria")
    List<Serie> obterSeriesPorCategoria(Categoria categoria);

    @Query("""
            SELECT e FROM Serie s
            JOIN s.episodios e
            WHERE e.titulo 
            ILIKE %:trechoEpisodio%""")
    List<Episodio> episodiosPorTrecho(String trechoEpisodio);

    @Query("""
            SELECT e FROM Serie s
            JOIN s.episodios e
            WHERE s = :serie
            ORDER BY e.avaliacao DESC
            LIMIT 5
            """)
    List<Episodio> topEpisodiosPorSerie(Serie serie);

    @Query("""
            SELECT e FROM Serie s
            JOIN s.episodios e
            WHERE s = :serie
            AND YEAR(e.dataLancamento) >= :anoLancamento
            """)
    List<Episodio> topEpisodiosPorSerieEAno(Serie serie , int anoLancamento);

    @Query("""
            SELECT e FROM Serie s
            JOIN s.episodios e
            WHERE s.id = :serieId
            AND e.temporada = :temporadaId
            """)
    List<Episodio> obterEpisodiosPorTemporada(Long serieId , Long temporadaId);
}
