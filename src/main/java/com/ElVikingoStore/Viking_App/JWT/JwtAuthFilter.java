package com.ElVikingoStore.Viking_App.JWT;
import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JwtAuthFilter extends OncePerRequestFilter {

    @Autowired
    private JwtTokenProvider tokenProvider;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    private static final Logger log = LoggerFactory.getLogger(JwtAuthFilter.class);

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        if (shouldNotFilter(request)) {
            filterChain.doFilter(request, response);
            return;
        }

        String authHeader = request.getHeader("Authorization");
        log.info("Authorization header recibido: " + authHeader);

        if (authHeader != null && authHeader.toLowerCase().startsWith("bearer")) {
            String token = tokenProvider.parseBearerToken(authHeader);
            log.info("Token extraído: " + token);

            if (token != null && tokenProvider.validateToken(token)) {
                log.info("Token is valid");
                try {
                    // get username from token
                    String username = tokenProvider.getUsernameFromJWT(token);
                    // load user associated with token
                    UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);

                    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities()
                    );
                    authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    // set spring security
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                } catch (Exception e) {
                    log.error("No se pudo establecer la autenticación del usuario en el contexto de seguridad", e);
                }
            } else {
                log.warn("Token no válido o nulo");
            }
        } else {
            log.warn("Authorization header no encontrado o no comienza con 'Bearer'");
        }

        filterChain.doFilter(request, response);
    }
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getServletPath();
        return path.startsWith("/auth/");
    }
}