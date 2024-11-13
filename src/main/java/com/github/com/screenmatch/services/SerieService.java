package com.github.com.screenmatch.services;

import com.github.com.screenmatch.dtos.SerieResponseDTO;
import com.github.com.screenmatch.models.DadosSerie;
import com.github.com.screenmatch.models.Serie;
import com.github.com.screenmatch.repositories.ISerieRepository;
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
        return serieRepository.findAll()
                .stream()
                .map(s -> new SerieResponseDTO(
                s.getId() ,
                s.getTitulo() ,
                s.getTotalTemporadas() ,
                s.getAvaliacao() ,
                s.getGenero() ,
                s.getAtores() ,
                s.getPoster() ,
                s.getSinopse()
        )).toList();
    }

    @Override
    public DadosSerie criarSerie(DadosSerie dadosSerie) {

        final Serie serieASerCriada = new Serie(dadosSerie);
        final Serie serieCriada = serieRepository.save(serieASerCriada);
      //  return new DadosSerie(serieCriada);
        return null;//serieCriada;
    }
}
