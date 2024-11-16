package com.github.com.screenmatch.dtos;

import com.github.com.screenmatch.models.Episodio;

public record EpisodioResponseDTO(
        Integer temporada ,
        Integer numeroEpisodio,
        String titulo
) {
    public EpisodioResponseDTO(Episodio entity) {
        this(
            entity.getTemporada() ,
            entity.getNumeroEpisodio() ,
            entity.getTitulo()
        );
    }
}
