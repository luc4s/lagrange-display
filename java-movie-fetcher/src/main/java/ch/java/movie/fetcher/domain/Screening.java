package ch.java.movie.fetcher.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import java.util.Date;

@Getter
public class Screening {

    @JsonProperty("date")
    private Date date;

    @JsonProperty("language")
    private String language;

    @JsonProperty("subtitles")
    private String subtitles;
}
