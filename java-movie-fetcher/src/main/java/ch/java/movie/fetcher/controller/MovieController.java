package ch.java.movie.fetcher.controller;

import ch.java.movie.fetcher.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MovieController {

    @Autowired
    private MovieService movieService;

    // TODO : implement a post endpoint to dynamically fetch the movie schedule
}
