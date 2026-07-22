package com.atman.server.Security.Service;


import com.atman.server.Admin.Enum.UserRole;
import com.atman.server.Security.DTO.LogInCredentialsDTO;
import com.atman.server.Security.DTO.TokenCredentialsDTO;

import javax.security.auth.login.AccountLockedException;
import javax.security.auth.login.CredentialException;

public interface AuthService {
    TokenCredentialsDTO authenticate(LogInCredentialsDTO logInCredentialsDTO) throws AccountLockedException, CredentialException;

    void logout(String contactNumber, UserRole userRole);

    TokenCredentialsDTO refresh(String refreshToken) throws CredentialException, AccountLockedException;
}
