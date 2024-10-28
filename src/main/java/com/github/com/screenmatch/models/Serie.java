package com.github.com.screenmatch.models;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record Serie(
        @JsonAlias("Title") String titulo ,
        @JsonAlias("totalSeasons") Integer totalTemporadas ,
        @JsonAlias("imdbRating") String avaliacao
        ) {
}
