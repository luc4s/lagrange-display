package ch.java.movie.fetcher.controller;

import ch.java.movie.fetcher.domain.Login;
import ch.java.movie.fetcher.security.JwtUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Objects;

@RestController
@RequestMapping("/token")
public class AuthController {

    @PostMapping
    public ResponseEntity<String> getToken(@RequestBody Login login) {
        if (!Objects.equals(login.getUsername(), "lagrange") || !Objects.equals(login.getPassword(), "lachancla")) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity<>(JwtUtil.generateToken(login), HttpStatus.OK);
    }
}