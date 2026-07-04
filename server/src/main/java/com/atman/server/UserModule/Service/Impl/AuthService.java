package com.atman.server.UserModule.Service.Impl;

import com.atman.server.UserModule.DTO.*;

public interface AuthService {
    UserCredentialResponse registerUser(UserRegistrationRequestDto userRegistrationRequestDto);

    UserCredentialResponse loginUser(UserLoginRequestDto userLoginRequestDto);

    UserCredentialResponse resetPassword(UserResetPasswordRequestDto userResetPasswordRequestDto);

}
