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
    SerieResponseDTO obterPorId(Long id);
    List<EpisodioResponseDTO> obterTodasTemporadas(Long id);

    DadosSerie criarSerie(DadosSerie dadosSerie);

}
