package com.github.com.screenmatch.models;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record Temporada(
        @JsonAlias("Season") Integer numero ,
        @JsonAlias("Episodes") List<Episodio> episodios
) {
}
