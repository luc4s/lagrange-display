package ch.java.movie.fetcher.service;

import ch.java.movie.fetcher.domain.Movie;
import ch.java.movie.fetcher.domain.MovieSchedule;
import ch.java.movie.fetcher.domain.Screening;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Locale;

@Slf4j
@Service
public class ImageService {

    private static final int MOVIE_LIST_Y_OFFSET = 120;
    private static final int TITLE_FONT_SIZE = 40;
    private static final int TITLE_Y_OFFSET = 35;
    private static final int MAX_FONT_SIZE = 50;
    private static final int DESCRIPTION_FONT_SIZE = 20;
    private static final int TAB_SIZE = 10;

    @Value("${OUTPUT_IMAGE_FILE_PATH:./movies-program.bmp}")
    private String outputFilePath;

    @Value("${WIDTH:984}")
    private int width;

    @Value("${HEIGHT:1305}")
    private int height;

    @Value("${FONT:Text}")
    private String font;

    public void saveImageBMP(MovieSchedule movieSchedule) {
        this.saveImageBMP(this.createImageBMPFromMovieSchedule(movieSchedule), this.outputFilePath);
    }

    public void saveImageBMP(BufferedImage imagePNG) {
        this.saveImageBMP(this.createImageBMPFromPNG(imagePNG), this.outputFilePath);
    }

    private BufferedImage createImageBMPFromPNG(BufferedImage image) {
        BufferedImage newImage = new BufferedImage(this.width, this.height, BufferedImage.TYPE_BYTE_BINARY);
        Graphics g = newImage.getGraphics();
        g.drawImage(image, 0, 0, null);
        g.dispose();
        return newImage;
    }

    private BufferedImage createImageBMPFromMovieSchedule(MovieSchedule movieSchedule) {

        // scale the font size to fit the number of lines composing the movie list
        double totalLines = Arrays.stream(movieSchedule.getMovies())
            .reduce(0, (subtotal, movie) -> subtotal + 1 + movie.getScreenings().length, Integer::sum);
        int fontSize = (int) Math.min(MAX_FONT_SIZE, (this.height - MOVIE_LIST_Y_OFFSET) / totalLines);

        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_BINARY);
        Graphics g = image.getGraphics();
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, this.width, this.height);

        g.setColor(Color.BLACK);
        g.setFont(new Font(this.font, Font.BOLD, TITLE_FONT_SIZE));
        g.drawString(movieSchedule.getTitle(), TAB_SIZE, TITLE_Y_OFFSET);
        g.setFont(new Font(this.font, Font.PLAIN, DESCRIPTION_FONT_SIZE));
        g.drawString(movieSchedule.getDescription(), TAB_SIZE, TITLE_Y_OFFSET + DESCRIPTION_FONT_SIZE);
        g.drawString(new SimpleDateFormat("dd MMMM yyyy HH:mm", new Locale("fr", "FR")).format(movieSchedule.getDate()), TAB_SIZE, TITLE_Y_OFFSET + 2 * DESCRIPTION_FONT_SIZE);

        int yCursor = MOVIE_LIST_Y_OFFSET;
        for (Movie movie : movieSchedule.getMovies()) {
            g.setFont(new Font(this.font, Font.BOLD, fontSize - 8));
            g.drawString(movie.getTitle(), 4 * TAB_SIZE, yCursor);
            yCursor += fontSize;
            for (Screening screening : movie.getScreenings()) {
                g.setFont(new Font(this.font, Font.PLAIN, fontSize - 20));
                g.drawString(new SimpleDateFormat("dd MMMM yyyy HH:mm", new Locale("fr", "FR")).format(screening.getDate())
                             + " - " + screening.getLanguage() + (!screening.getSubtitles().isEmpty() ? " - sous-titres : " + screening.getSubtitles() : ""), 6 * TAB_SIZE, yCursor);
                yCursor += fontSize;
            }
        }

        g.dispose();
        return image;
    }

    private BufferedImage rotateClockwise90(BufferedImage src) {
        int w = src.getWidth();
        int h = src.getHeight();
        BufferedImage dest = new BufferedImage(h, w, src.getType());
        for (int y = 0; y < h; y++)
            for (int x = 0; x < w; x++)
                dest.setRGB(y, w - x - 1, src.getRGB(x, y));
        return dest;
    }

    private void saveImageBMP(BufferedImage image, String filePath) {
        try {
            String format = "bmp";
            File outputFile = new File(filePath);
            ImageIO.write(this.rotateClockwise90(image), format, outputFile);
            log.info("Image saved successfully to: {}", filePath);
        } catch (IOException e) {
            log.error("Error while saving the image", e);
        }
    }
}
