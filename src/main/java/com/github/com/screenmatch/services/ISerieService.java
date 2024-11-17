package com.github.com.screenmatch.services;

import com.github.com.screenmatch.dtos.EpisodioResponseDTO;
import com.github.com.screenmatch.dtos.SerieResponseDTO;
import com.github.com.screenmatch.models.DadosSerie;

import java.util.List;

public interface ISerieService {
    List<SerieResponseDTO> obterTodasAsSeries();
    List<SerieResponseDTO> obterTop5Series();
//    List<SerieResponseDTO> obterLancamentos();
    List<SerieResponseDTO> encontrarEpisodiosMaisRecentes();
    List<SerieResponseDTO> obterSeriesPorCategoria(String categoriaNome);
    SerieResponseDTO obterPorId(Long id);

    List<EpisodioResponseDTO> obterTodasTemporadas(Long id);
    List<EpisodioResponseDTO> obterTemporadaPorNumero(Long serieId , Long temporadaId);

    DadosSerie criarSerie(DadosSerie dadosSerie);

}
