package com.atman.server.UserModule.DTO;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Data
@Builder
public class UserProfileUpdationRequestDto {
    @NotNull(message = "User id cannot be null")
    private UUID userId;
    private String username;
}
