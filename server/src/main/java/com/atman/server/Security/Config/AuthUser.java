package com.atman.server.Security.Config;

import com.atman.server.Admin.Enum.AccountStatus;
import com.atman.server.Admin.Enum.UserRole;

import java.util.UUID;

public interface AuthUser {
    UUID getId();

    String getContactNumber();

    String getPassword();

    UserRole getUserRole();

    AccountStatus getAccountStatus();

}
