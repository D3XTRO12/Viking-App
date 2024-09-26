package com.ElVikingoStore.Viking_App.JWT;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.SecretKey;

import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.ElVikingoStore.Viking_App.Exception.ApiException;

import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.log4j.Log4j2;

@Component
@Log4j2
public class JwtTokenProvider {


    @Value("${security.jwt.secret-key}")
    private String secretKey;

    @Value("${security.jwt.expiration-time}")
    private long jwtExpirationInMs;

    // generate token
    public String generateToken(Authentication authentication) {
        String username = authentication.getName();
        String token = Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpirationInMs))
                .signWith(signWithKey(), SignatureAlgorithm.HS512)
                .compact();
        log.info("Generated token: " + token);
        return token;
    }

    // get username from the token
    public String getUsernameFromJWT(String token){
        log.info("Parsing username fro token: " + token);
        Claims claims = Jwts.parser()
                .verifyWith(signWithKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();

        return claims.getSubject();
    }

    // validate JWT token
    public boolean validateToken(String token) {
        if (!StringUtils.hasText(token)) {
            log.warn("Invalid JWT token");
            return false;
        }
        log.info("Validating token: " + token);
        try {
            Claims claims = Jwts.parser()
                    .verifyWith(signWithKey())
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();

            return !claims.getExpiration().before(new Date());
        } catch (MalformedJwtException ex) {
            log.warn("Token malformado: " + token, ex);
            throw new ApiException(HttpStatus.BAD_REQUEST, "Invalid JWT token");
        } catch (ExpiredJwtException ex) {
            throw new ApiException(HttpStatus.BAD_REQUEST, "Expired JWT token");
        } catch (UnsupportedJwtException ex) {
            throw new ApiException(HttpStatus.BAD_REQUEST, "Unsupported JWT token");
        } catch (IllegalArgumentException ex) {
            throw new ApiException(HttpStatus.BAD_REQUEST, "JWT claims string is empty.");
        } catch (SignatureException ex) {
            log.error("JWT signature exception", ex);
            throw new ApiException(HttpStatus.BAD_REQUEST, "falla api exception");
        }
    }

    public String parseBearerToken(String bearerToken) {
        if (StringUtils.hasText(bearerToken) && bearerToken.toLowerCase().startsWith("bearer")) {
            String[] parts = bearerToken.split("\\s+");
            if (parts.length == 2) {
                String token = parts[1].trim();
                log.info("Token parsed from Bearer: " + token);
                return token;
            }
        }
        return null;
    }

    private SecretKey signWithKey() {
        log.info("Secret key (first 10 characters): " + secretKey.substring(0, Math.min(10, secretKey.length())));
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

}