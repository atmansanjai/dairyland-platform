package com.atman.server.Security.Service;

import com.atman.server.Admin.Enum.AccountStatus;
import com.atman.server.Admin.Enum.UserRole;
import com.atman.server.Security.Config.AuthUser;
import com.atman.server.Security.Config.IdentityService;
import com.atman.server.Security.Config.JwtService;
import com.atman.server.Security.DTO.LogInCredentialsDTO;
import com.atman.server.Security.DTO.TokenCredentialsDTO;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class AuthServiceImpl implements AuthService {

    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final IdentityService identityService;

    @Override
    public TokenCredentialsDTO authenticate(LogInCredentialsDTO logInCredentialsDTO) {
        AuthUser authUser = identityService.loadUserByContactNumber(logInCredentialsDTO.getContactNumber(), logInCredentialsDTO.getRole());

        if(AccountStatus.SUSPEND.equals(authUser.getAccountStatus())) {
            throw new IllegalStateException("Account is Suspended");
        }

        if(!passwordEncoder.matches(logInCredentialsDTO.getPassword(), authUser.getPassword())) {
            throw new BadCredentialsException("Invalid Credentials");
        }

        return jwtService.tokenBuilder(authUser);
    }

    @Override
    public void logout(String contactNumber, UserRole userRole) {
        identityService.logoutByContactNumber(contactNumber, userRole);
    }

    @Override
    public TokenCredentialsDTO refresh(String refreshToken) {
        if(!jwtService.validateToken(refreshToken)) {
            throw new BadCredentialsException("Invalid or expired refresh token");
        }

        String contactNumber = jwtService.extractContactNumber(refreshToken);
        String userRole = jwtService.extractRole(refreshToken);

        AuthUser authUser = identityService.loadUserByContactNumber(contactNumber, UserRole.valueOf(userRole));

        if(AccountStatus.SUSPEND.equals(authUser.getAccountStatus())) {
            throw new IllegalStateException("Account is Suspended");
        }

        return jwtService.tokenBuilder(authUser);
    }
}