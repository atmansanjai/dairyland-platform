package com.atman.server.Security.DTO;

import com.atman.server.Security.Config.AuthUser;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class TokenCredentialsDTO {
    private AuthUser user;
    private String accessToken;
    private String refreshToken;
}
