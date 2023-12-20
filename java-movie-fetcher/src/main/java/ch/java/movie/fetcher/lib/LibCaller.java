package ch.java.movie.fetcher.lib;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

@Slf4j
@Service
public class LibCaller {

    public void displayBMP() {
        try {

            ProcessBuilder processBuilder = new ProcessBuilder("./epd");
            processBuilder.redirectErrorStream(true);
            Process process = processBuilder.start();

            InputStream inputStream = process.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

            // Read and log the output
            String line;
            while ((line = reader.readLine()) != null) {
                log.info("epd logs : {}", line);
            }

            // Wait for the process to finish
            int exitCode = process.waitFor();
            log.info("External Process exited with code: {}", exitCode);

        } catch (IOException | InterruptedException e) {
            Thread.currentThread().interrupt();
            log.error("Error executing epd", e);
        }
    }
}
