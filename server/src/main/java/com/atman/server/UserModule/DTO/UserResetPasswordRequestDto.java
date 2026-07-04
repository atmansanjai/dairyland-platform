package com.atman.server.UserModule.DTO;


import com.atman.server.UserModule.Enum.UserRole;
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
public class UserResetPasswordRequestDto {
    @NotNull(message = "User ID cannot be null")
    private UUID userId;

    @NotNull(message = "User role cannot be null")
    private UserRole userRole;

    @NotNull(message = "Old password cannot be null")
    private String oldPassword;

    @NotNull(message = "New password cannot be null")
    private String newPassword;
}
