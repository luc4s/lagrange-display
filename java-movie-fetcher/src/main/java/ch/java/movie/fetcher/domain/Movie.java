package ch.java.movie.fetcher.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class Movie {

    @JsonProperty("id_suisa")
    private String idSuisa;

    @JsonProperty("title")
    private String title;

    @JsonProperty("screenings")
    private Screening[] screenings;
}
