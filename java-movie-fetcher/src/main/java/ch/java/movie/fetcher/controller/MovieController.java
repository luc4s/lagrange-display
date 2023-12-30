package ch.java.movie.fetcher.controller;

import ch.java.movie.fetcher.domain.MovieSchedule;
import ch.java.movie.fetcher.lib.LibCaller;
import ch.java.movie.fetcher.service.ImageService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/movie")
public class MovieController {

    @Autowired
    private ImageService imageService;

    @Autowired
    private LibCaller libCaller;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Void> updateMovieSchedule(@Valid @RequestBody MovieSchedule movieSchedule) {
        new Thread(() -> {
            this.imageService.saveImageBMP(movieSchedule);
            this.libCaller.displayBMP();
        }).start();
        return ResponseEntity.noContent().build();
    }
}
