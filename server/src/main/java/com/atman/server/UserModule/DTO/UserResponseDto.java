package com.atman.server.UserModule.DTO;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Data
@Builder
public class UserResponseDto {
    private UUID id;
    private String username;
    private String contactNumber;
    private String userRole;
}
