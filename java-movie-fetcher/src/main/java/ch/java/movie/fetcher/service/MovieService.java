package ch.java.movie.fetcher.service;

import ch.java.movie.fetcher.domain.MovieSchedule;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

@Slf4j
@Service
public class MovieService {

    @Value("${URL:https://cinelagrange.ch/feed}")
    private String urlJSON;

    @Value("${URL:http://192.168.1.108:3001?display=compact}")
    private String urlPNG;

    public MovieSchedule fetchMovieScheduleJSON() {

        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {

            HttpGet httpGet = new HttpGet(this.urlJSON);
            CloseableHttpResponse response = httpClient.execute(httpGet);

            if (response.getStatusLine().getStatusCode() == 200) {
                String json = EntityUtils.toString(response.getEntity());
                ObjectMapper mapper = new ObjectMapper();
                return mapper.readValue(json, MovieSchedule.class);
            } else {
                log.error("Error while fetching the resource: " + response.getStatusLine().getStatusCode());
            }

        } catch (Exception e) {
            log.error("Error while fetching the resource", e);
        }

        return null;
    }

    public BufferedImage fetchMovieSchedulePNG() {

//        System.setProperty("webdriver.chrome.driver", "/usr/local/bin/chromedriver");
//        ChromeOptions options = new ChromeOptions();
//        WebDriver driver = new ChromeDriver(options);
//        options.addArguments("--headless", "--disable-gpu", "--window-size=984x1305", "--screenshot=output.png", "--disable-software-rasterizer");
//
//        try {
//            driver.get(this.urlPNG);
//            byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
//            return ImageIO.read(new ByteArrayInputStream(screenshot));
//        } catch (Exception e) {
//            log.error("Error while fetching the resource", e);
//        }

        try {

            String[] command = {
                "chromium-browser",
                "--headless",
                "--disable-gpu",
                "--screenshot=output.png",
                "--window-size=984x1305",
                "--disable-software-rasterizer",
                this.urlPNG
            };

            ProcessBuilder processBuilder = new ProcessBuilder(command);
            Process process = processBuilder.start();
            process.waitFor();

            byte[] screenshot = Files.readAllBytes(Paths.get("output.png"));
            return ImageIO.read(new ByteArrayInputStream(screenshot));

        } catch (Exception e) {
            Thread.currentThread().interrupt();
            log.error("Error while fetching the resource", e);
        }

        return null;
    }
}

