package com.atman.server.UserModule.DTO;

import com.atman.server.UserModule.Enum.UserRole;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
@Builder
public class UserLoginRequestDto {
    @NotNull(message = "Contact number cannot be null")
    private String contactNumber;
    @NotNull(message = "Password cannot be null")
    private String password;
    @NotNull(message = "User role cannot be null")
    private UserRole userRole;
}
