package com.kodilla.ecommercee.security;

import com.kodilla.ecommercee.domain.User;
import com.kodilla.ecommercee.repository.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class TokenAuthFilter extends OncePerRequestFilter {

    private final UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        String method = request.getMethod();
        String path = request.getRequestURI();

        if (method.equals("POST") && path.equals("/api/v1/users")) {
            filterChain.doFilter(request, response);
            return;
        }

        if (List.of("POST", "PUT", "DELETE").contains(method)) {
            String header = request.getHeader("Authorization");

            if (header == null || !header.startsWith("Bearer ")) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write("Missing or malformed Authorization header");
                return;
            }

            String token = header.substring("Bearer ".length());
            Optional<User> userOpt = userRepository.findByToken(token);

            if (userOpt.isEmpty() || userOpt.get().isBlocked()
                    || userOpt.get().getTokenExpiresAt().isBefore(LocalDateTime.now())) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write("Invalid, expired or blocked token");
                return;
            }

            request.setAttribute("authenticatedUser", userOpt.get());
        }

        filterChain.doFilter(request, response);
    }
}

