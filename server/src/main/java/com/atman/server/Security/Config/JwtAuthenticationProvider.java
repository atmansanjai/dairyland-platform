package com.atman.server.Security.Config;

import com.atman.server.Admin.Enum.UserRole;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationProvider implements AuthenticationProvider {

    private final JwtService jwtService;
    private final IdentityService identityService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String token = (String) authentication.getCredentials();

        if(!jwtService.validateToken(token)) {
            throw new BadCredentialsException("Invalid or expired JWT token");
        }

        try {
            String contactNumber = jwtService.extractContactNumber(token);
            String roleStr = jwtService.extractRole(token);

            if(contactNumber == null || roleStr == null) {
                throw new BadCredentialsException("Token missing critical claims");
            }

            UserRole userRole = UserRole.valueOf(roleStr);

            AuthUser authUser = identityService.loadUserByContactNumber(contactNumber, userRole);
            List<SimpleGrantedAuthority> authorities = List.of(new SimpleGrantedAuthority("ROLE_" + authUser.getUserRole()
                                                                                                            .name()));

            return new JwtAuthenticationToken(authUser, authorities, token);

        } catch(Exception e) {
            throw new BadCredentialsException("Authentication failed: " + e.getMessage(), e);
        }
    }

    @Override
    public boolean supports(@NonNull Class<?> authentication) {
        return JwtAuthenticationToken.class.isAssignableFrom(authentication);
    }
}