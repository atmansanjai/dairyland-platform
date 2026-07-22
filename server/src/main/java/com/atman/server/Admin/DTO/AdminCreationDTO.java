package com.atman.server.Admin.DTO;

import com.atman.server.Admin.Enum.UserRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class AdminCreationDTO {
    private String username;
    private String contactNumber;
    private String password;
    private UserRole role;
}
