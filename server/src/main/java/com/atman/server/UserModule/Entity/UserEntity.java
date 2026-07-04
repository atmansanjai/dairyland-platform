package com.atman.server.UserModule.Entity;


import com.atman.server.UserModule.Enum.AccountStatus;
import com.atman.server.UserModule.Enum.UserRole;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;


@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
@SuperBuilder
@Entity
@Table(name = "users", indexes = @Index(name = "idx_contact_number", columnList = ("contact_number")))
public class UserEntity extends BaseEntity {
    @Column(name = "username", nullable = false, length = 50)
    private String username;

    @Column(name = "contact_number", nullable = false, length = 10, unique = true)
    private String contactNumber;

    @Column(name = "password", nullable = false, length = 100)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "user_role", nullable = false)
    private UserRole userRole;

    @Enumerated(EnumType.STRING)
    @Column(name = "account_status", nullable = false)
    private AccountStatus accountStatus;
}
