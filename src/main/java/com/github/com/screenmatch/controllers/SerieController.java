package com.github.com.screenmatch.controllers;

import com.github.com.screenmatch.dtos.EpisodioResponseDTO;
import com.github.com.screenmatch.dtos.SerieResponseDTO;
import com.github.com.screenmatch.services.ISerieService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/series")
public class SerieController {

    private final ISerieService serieService;

    public SerieController(ISerieService serieService) {
        this.serieService = serieService;
    }

    @GetMapping
    public ResponseEntity<List<SerieResponseDTO>> obterTodasAsSeries() {
        return ResponseEntity.ok(serieService.obterTodasAsSeries());
    }

    @GetMapping("/top5")
    public ResponseEntity<List<SerieResponseDTO>> obterTop5Series() {
        return ResponseEntity.ok(serieService.obterTop5Series());
    }

    @GetMapping("/lancamentos")
    public ResponseEntity<List<SerieResponseDTO>> obterLancamentos() {
        return ResponseEntity.ok(serieService.encontrarEpisodiosMaisRecentes());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SerieResponseDTO> obterPorId(@PathVariable Long id) {
        return ResponseEntity.ok(serieService.obterPorId(id));
    }

    @GetMapping("/{id}/temporadas/todas")
    public ResponseEntity<List<EpisodioResponseDTO>> obterTodasTemporadas(@PathVariable Long id) {
        return ResponseEntity.ok(serieService.obterTodasTemporadas(id));
    }
}
