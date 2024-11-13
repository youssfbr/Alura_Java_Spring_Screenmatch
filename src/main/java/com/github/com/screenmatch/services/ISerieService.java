package com.github.com.screenmatch.services;

import com.github.com.screenmatch.dtos.SerieResponseDTO;
import com.github.com.screenmatch.models.DadosSerie;
import com.github.com.screenmatch.models.Serie;

import java.util.List;

public interface ISerieService {
    List<SerieResponseDTO> obterSeries();
    DadosSerie criarSerie(DadosSerie dadosSerie);
}
