package com.atman.server.UserModule.Service;

import com.atman.server.UserModule.Configuration.JwtConfig;
import com.atman.server.UserModule.Service.Impl.TokenService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.function.Function;

@RequiredArgsConstructor
@Service
public class TokenServiceImpl implements TokenService {

    private final JwtConfig jwtConfig;

    @Override
    public String generateAccessToken(String contactNumber) {
        return buildToken(contactNumber, jwtConfig.getTokenExpiration());
    }

    @Override
    public String generateRefreshToken(String contactNumber) {
        return buildToken(contactNumber, jwtConfig.getRefreshTokenExpiration());
    }

    private String buildToken(String contactNumber, long expiration) {
        return Jwts.builder().subject(contactNumber).issuedAt(new Date(System.currentTimeMillis())).expiration(new Date(System.currentTimeMillis() + expiration)).signWith(getSignInKey()).compact();
    }

    @Override
    public boolean verifyToken(String token) {
        try {
            Jwts.parser().verifyWith(getSignInKey()).build().parseSignedClaims(token);
            return true;
        } catch(Exception e) {
            return false;
        }
    }

    @Override
    public String getContactNumberFromToken(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = Jwts.parser().verifyWith(getSignInKey()).build().parseSignedClaims(token).getPayload();
        return claimsResolver.apply(claims);
    }

    private SecretKey getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(jwtConfig.getSecretKey());
        return Keys.hmacShaKeyFor(keyBytes);
    }
}