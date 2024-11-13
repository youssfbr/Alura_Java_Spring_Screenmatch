package com.github.com.screenmatch.controllers;

import com.github.com.screenmatch.dtos.SerieResponseDTO;
import com.github.com.screenmatch.services.ISerieService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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
}
