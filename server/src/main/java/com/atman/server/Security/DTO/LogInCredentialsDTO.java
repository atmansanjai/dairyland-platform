package com.atman.server.Security.DTO;

import com.atman.server.Admin.Enum.UserRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class LogInCredentialsDTO {
    private String contactNumber;
    private String password;
    private UserRole role;
}
