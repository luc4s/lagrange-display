package ch.java.movie.fetcher.service;

import ch.java.movie.fetcher.domain.MovieSchedule;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class MovieService {

    @Value("${URL:https://cinelagrange.ch/feed}")
    private String url;

    public MovieSchedule fetchMovieSchedule() {

        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {

            HttpGet httpGet = new HttpGet(this.url);
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
}

