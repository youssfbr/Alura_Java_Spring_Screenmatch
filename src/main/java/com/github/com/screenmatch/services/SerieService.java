package com.github.com.screenmatch.services;

import com.github.com.screenmatch.dtos.SerieResponseDTO;
import com.github.com.screenmatch.models.DadosSerie;
import com.github.com.screenmatch.models.Serie;
import com.github.com.screenmatch.repositories.ISerieRepository;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SerieService implements ISerieService {

    private final ISerieRepository serieRepository;

    public SerieService(ISerieRepository serieRepository) {
        this.serieRepository = serieRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<SerieResponseDTO> obterTodasAsSeries() {
        return getList(serieRepository.findAll());
    }

    @Override
    @Transactional(readOnly = true)
    public List<SerieResponseDTO> obterTop5Series() {
        return getList(serieRepository.findTop5ByOrderByAvaliacaoDesc());
    }

    @Override
    public DadosSerie criarSerie(DadosSerie dadosSerie) {

        final Serie serieASerCriada = new Serie(dadosSerie);
        final Serie serieCriada = serieRepository.save(serieASerCriada);
      //  return new DadosSerie(serieCriada);
        return null;//serieCriada;
    }

    @NotNull
    private List<SerieResponseDTO> getList(List<Serie> series) {
        return series.stream()
                .map(SerieResponseDTO::new)
                .toList();
    }
}
