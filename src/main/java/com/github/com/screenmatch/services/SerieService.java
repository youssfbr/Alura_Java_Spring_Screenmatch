package com.github.com.screenmatch.services;

import com.github.com.screenmatch.models.DadosSerie;
import com.github.com.screenmatch.models.Serie;
import com.github.com.screenmatch.repositories.ISerieRepository;
import org.springframework.stereotype.Service;

@Service
public class SerieService implements ISerieService {

    private ISerieRepository serieRepository;

    public SerieService(ISerieRepository serieRepository) {
        this.serieRepository = serieRepository;
    }

    @Override
    public DadosSerie criarSerie(DadosSerie dadosSerie) {

        final Serie serieASerCriada = new Serie(dadosSerie);
        final Serie serieCriada = serieRepository.save(serieASerCriada);
      //  return new DadosSerie(serieCriada);
        return null;
    }
}
