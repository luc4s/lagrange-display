package ch.java.movie.fetcher.scheduler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;
import ch.java.movie.fetcher.service.MovieService;

@Slf4j
@Configuration
public class Scheduler {

    @Autowired
    private MovieService movieService;

    @Scheduled(fixedDelayString = "${FREQUENCY:3600000}")
    public void triggerScheduler() {
        this.movieService.fetchMovieSchedule();
    }
}
