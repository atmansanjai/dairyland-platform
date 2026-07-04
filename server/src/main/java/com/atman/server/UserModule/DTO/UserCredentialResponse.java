package com.atman.server.UserModule.DTO;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
@Builder
public class UserCredentialResponse {
    private UserResponseDto userResponseDto;
    private String accessToken;
    private String refreshToken;
}
