package com.atman.server.UserModule.DTO;

import com.atman.server.UserModule.Enum.AccountStatus;
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
public class UserAccountStatusRequestDto {
    @NotNull(message = "User id cannot be null")
    private UUID userId;

    @NotNull(message = "Account status cannot be null")
    private AccountStatus accountStatus;
}
