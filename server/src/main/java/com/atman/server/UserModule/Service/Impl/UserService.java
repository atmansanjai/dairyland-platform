package com.atman.server.UserModule.Service.Impl;

import com.atman.server.UserModule.DTO.UserAccountStatusRequestDto;
import com.atman.server.UserModule.DTO.UserDeletionRequestDto;
import com.atman.server.UserModule.DTO.UserProfileUpdationRequestDto;
import com.atman.server.UserModule.DTO.UserResponseDto;

import java.util.UUID;

public interface UserService {

    UserResponseDto updateUserDetails(UserProfileUpdationRequestDto userProfileUpdationRequestDto);

    UUID deleteUser(UserDeletionRequestDto userDeletionRequestDto);

    UserResponseDto updateAccountStatus(UserAccountStatusRequestDto userAccountStatusRequestDto);
}
