package com.atman.server.Security.Controller;

import com.atman.server.Admin.Enum.UserRole;
import com.atman.server.Security.DTO.LogInCredentialsDTO;
import com.atman.server.Security.DTO.TokenCredentialsDTO;
import com.atman.server.Security.Service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.security.auth.login.AccountLockedException;
import javax.security.auth.login.CredentialException;
import java.time.Duration;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @Value("${spring.application.security.jwt.access-expiration}")
    private long accessTokenExpiration;

    @Value("${spring.application.security.jwt.refresh-expiration}")
    private long refreshTokenExpiration;

    @PostMapping("/login")
    public ResponseEntity<Void> authenticate(@RequestBody LogInCredentialsDTO logInCredentialsDTO) throws AccountLockedException, CredentialException {
        TokenCredentialsDTO tokens = authService.authenticate(logInCredentialsDTO);

        ResponseCookie accessTokenCookie = ResponseCookie.from("accessToken", tokens.getAccessToken())
                                                         .httpOnly(true)
                                                         .secure(false) // Set to true in production with HTTPS
                                                         .path("/")
                                                         .maxAge(Duration.ofMillis(accessTokenExpiration))
                                                         .sameSite("Lax")
                                                         .build();

        ResponseCookie refreshTokenCookie = ResponseCookie.from("refreshToken", tokens.getRefreshToken())
                                                          .httpOnly(true)
                                                          .secure(false) // Set to true in production with HTTPS
                                                          .path("/")
                                                          .maxAge(Duration.ofMillis(refreshTokenExpiration))
                                                          .sameSite("Lax")
                                                          .build();

        return ResponseEntity.ok()
                             .header(HttpHeaders.SET_COOKIE, accessTokenCookie.toString())
                             .header(HttpHeaders.SET_COOKIE, refreshTokenCookie.toString())
                             .build();
    }

    @PostMapping("/refresh")
    public ResponseEntity<Void> refresh(@CookieValue(name = "refreshToken", required = false) String refreshToken) throws AccountLockedException, CredentialException {
        if(refreshToken == null || refreshToken.isBlank()) {
            return ResponseEntity.status(401)
                                 .build();
        }

        TokenCredentialsDTO tokens = authService.refresh(refreshToken);

        ResponseCookie accessTokenCookie = ResponseCookie.from("accessToken", tokens.getAccessToken())
                                                         .httpOnly(true)
                                                         .secure(false)
                                                         .path("/")
                                                         .maxAge(Duration.ofMillis(accessTokenExpiration))
                                                         .sameSite("Lax")
                                                         .build();

        return ResponseEntity.ok()
                             .header(HttpHeaders.SET_COOKIE, accessTokenCookie.toString())
                             .build();
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@RequestParam String contactNumber, @RequestParam UserRole userRole) {

        authService.logout(contactNumber, userRole);

        ResponseCookie clearAccessToken = ResponseCookie.from("accessToken", "")
                                                        .httpOnly(true)
                                                        .secure(false)
                                                        .path("/")
                                                        .maxAge(Duration.ZERO)
                                                        .sameSite("Lax")
                                                        .build();

        ResponseCookie clearRefreshToken = ResponseCookie.from("refreshToken", "")
                                                         .httpOnly(true)
                                                         .secure(false)
                                                         .path("/")
                                                         .maxAge(Duration.ZERO)
                                                         .sameSite("Lax")
                                                         .build();

        return ResponseEntity.ok()
                             .header(HttpHeaders.SET_COOKIE, clearAccessToken.toString())
                             .header(HttpHeaders.SET_COOKIE, clearRefreshToken.toString())
                             .build();
    }
}
