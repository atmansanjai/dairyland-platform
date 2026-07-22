package com.atman.server.Security.Config;

import com.atman.server.Security.DTO.TokenCredentialsDTO;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Service
public class JwtService {
    private final SecretKey secretKey;
    private final long accessTokenExpiration;
    private final long refreshTokenExpiration;

    public JwtService(@Value("${spring.application.security.jwt.secret-key}") String secret, @Value("${spring.application.security.jwt.access-expiration}") long accessTokenExpiration, @Value("${spring.application.security.jwt.refresh-expiration}") long refreshTokenExpiration) {
        this.secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        this.accessTokenExpiration = accessTokenExpiration;
        this.refreshTokenExpiration = refreshTokenExpiration;
    }

    public String extractContactNumber(String token) {
        return extractAllClaims(token).getSubject();
    }

    public Claims extractAllClaims(String token) {
        return Jwts.parser()
                   .verifyWith(secretKey)
                   .build()
                   .parseSignedClaims(token)
                   .getPayload();
    }

    public String extractRole(String token) {
        return extractAllClaims(token).get("role", String.class);
    }

    public TokenCredentialsDTO tokenBuilder(AuthUser authUser) {
        String accessToken = generateAccessToken(authUser);
        String refreshToken = generateRefreshToken(authUser);
        return TokenCredentialsDTO.builder()
                                  .user(authUser)
                                  .accessToken(accessToken)
                                  .refreshToken(refreshToken)
                                  .build();
    }

    public String generateAccessToken(AuthUser user) {
        return buildToken(user, accessTokenExpiration);
    }

    public String generateRefreshToken(AuthUser user) {
        return buildToken(user, refreshTokenExpiration);
    }

    private String buildToken(AuthUser user, long expiration) {
        return Jwts.builder()
                   .subject(user.getContactNumber())
                   .claim("role", user.getUserRole()
                                      .name())
                   .issuedAt(new Date())
                   .expiration(new Date(System.currentTimeMillis() + expiration))
                   .signWith(secretKey)
                   .compact();
    }

    public boolean validateToken(String token) {
        try {
            extractAllClaims(token); // JJWT automatically validates the signature and expiration date
            return true;
        } catch(JwtException | IllegalArgumentException e) {
            // Token is invalid, tampered with, or expired
            return false;
        }
    }
}