package com.github.com.screenmatch.services;

import com.github.com.screenmatch.models.DadosSerie;
import com.github.com.screenmatch.models.Serie;
import com.github.com.screenmatch.repositories.ISerieRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SerieService implements ISerieService {

    private final ISerieRepository serieRepository;

    public SerieService(ISerieRepository serieRepository) {
        this.serieRepository = serieRepository;
    }

    @Override
    public List<Serie> buscarSeries() {
        return serieRepository.findAll();
    }

    @Override
    public DadosSerie criarSerie(DadosSerie dadosSerie) {

        final Serie serieASerCriada = new Serie(dadosSerie);
        final Serie serieCriada = serieRepository.save(serieASerCriada);
      //  return new DadosSerie(serieCriada);
        return null;
    }
}
