package ch.java.movie.fetcher.lib;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.io.IOException;

@Slf4j
@Service
public class LibCaller {

    public void displayBMP() {
        try {
            ProcessBuilder processBuilder = new ProcessBuilder("./epd");
            Process process = processBuilder.start();

            int exitCode = process.waitFor();
            log.info("Exit Code: " + exitCode);

        } catch (IOException | InterruptedException e) {
            Thread.currentThread().interrupt();
            log.error("Error executing epd", e);
        }
    }
}
