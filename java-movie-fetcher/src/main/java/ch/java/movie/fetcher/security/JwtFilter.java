package ch.java.movie.fetcher.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;
import java.util.Optional;

public class JwtFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        // Exclude authentication endpoint
        if (request.getRequestURI().equals("/api/token")) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = Optional.ofNullable(request.getHeader("Authorization"))
            .map(h -> h.replace("Bearer ", ""))
            .orElse(null);

        if (JwtUtil.tokenIsValid(token)) {
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken("username", null, null);
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        } else {
            response.setStatus(401);
            return;
        }

        filterChain.doFilter(request, response);
    }
}