package org.example.screenmetch2.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties (ignoreUnknown = true)
public record DadosEpisodio(
        @JsonAlias("Title") String title,
        @JsonAlias("Episode") Integer numero,
        @JsonAlias("imdbRating") String avaliacao,
        @JsonAlias("Released") String dataLancamento) {
}
