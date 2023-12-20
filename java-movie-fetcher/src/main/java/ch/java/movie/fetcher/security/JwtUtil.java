package ch.java.movie.fetcher.security;

import static io.jsonwebtoken.Jwts.parser;

import ch.java.movie.fetcher.domain.Login;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Date;

public class JwtUtil {
    private static final String SECRET = "yatangaki";
    private static final long EXPIRATION_TIME = 3_600_000;

    public static String generateToken(Login login) {
        return Jwts.builder()
            .setSubject(login.getUsername())
            .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
            .signWith(SignatureAlgorithm.HS512, SECRET)
            .compact();
    }

    public static boolean tokenIsValid(String token) {

        Claims claims;
        try {
            claims = parser()
                .setSigningKey(SECRET)
                .parseClaimsJws(token)
                .getBody();
        } catch (Exception e) {
            return false;
        }

        return !claims.getExpiration().before(new Date());
    }
}
