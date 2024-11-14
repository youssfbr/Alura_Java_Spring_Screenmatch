package com.github.com.screenmatch.dtos;

import com.github.com.screenmatch.models.Serie;
import com.github.com.screenmatch.models.enums.Categoria;

public record SerieResponseDTO(        
        Long id ,
        String titulo ,
        Integer totalTemporadas ,
        Double avaliacao ,
        Categoria genero ,
        String atores ,
        String poster ,
        String sinopse
) {
    public SerieResponseDTO (Serie entity) {
        this(
            entity.getId() ,
            entity.getTitulo() ,
            entity.getTotalTemporadas() ,
            entity.getAvaliacao() ,
            entity.getGenero() ,
            entity.getAtores() ,
            entity.getPoster() ,
            entity.getSinopse()
        );
    }
}
