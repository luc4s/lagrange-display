package ch.java.movie.fetcher.scheduler;

import ch.java.movie.fetcher.lib.LibCaller;
import ch.java.movie.fetcher.service.ImageService;
import ch.java.movie.fetcher.service.MovieService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;

@Slf4j
@Configuration
public class Scheduler {

    @Autowired
    private MovieService movieService;

    @Autowired
    private ImageService imageService;

    @Autowired
    private LibCaller libCaller;

    @Scheduled(fixedDelayString = "${FREQUENCY:3600000}")
    public void triggerScheduler() {
        this.imageService.saveImageBMP(this.movieService.fetchMovieSchedulePNG());
        this.libCaller.displayBMP();
    }
}
