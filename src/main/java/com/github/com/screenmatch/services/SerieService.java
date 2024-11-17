package com.github.com.screenmatch.services;

import com.github.com.screenmatch.dtos.EpisodioResponseDTO;
import com.github.com.screenmatch.dtos.SerieResponseDTO;
import com.github.com.screenmatch.models.DadosSerie;
import com.github.com.screenmatch.models.Serie;
import com.github.com.screenmatch.models.enums.Categoria;
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

//    @Override
//    @Transactional(readOnly = true)
//    public List<SerieResponseDTO> obterLancamentos() {
//        return getList(serieRepository.findTop5ByOrderByEpisodiosDataLancamentoDesc());
//    }

    @Override
    @Transactional(readOnly = true)
    public List<SerieResponseDTO> encontrarEpisodiosMaisRecentes() {
        return getList(serieRepository.encontrarEpisodiosMaisRecentes());
    }

    @Override
    @Transactional(readOnly = true)
    public List<SerieResponseDTO> obterSeriesPorCategoria(String categoriaNome) {
        final Categoria categoria = Categoria.fromStringPortugues(categoriaNome);
        return getList(serieRepository.obterSeriesPorCategoria(categoria));
    }

    @Override
    @Transactional(readOnly = true)
    public SerieResponseDTO obterPorId(Long id) {
        return serieRepository.findById(id)
                .map(SerieResponseDTO::new)
                .orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public List<EpisodioResponseDTO> obterTodasTemporadas(Long id) {
        return serieRepository.findById(id)
                .map(s -> s.getEpisodios()
                        .stream()
                        .map(EpisodioResponseDTO::new)
                        .toList())
                .orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public List<EpisodioResponseDTO> obterTemporadaPorNumero(Long serieId , Long temporadaId) {
        return serieRepository.obterEpisodiosPorTemporada(serieId , temporadaId)
                .stream()
                .map(EpisodioResponseDTO::new)
                .toList();
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
