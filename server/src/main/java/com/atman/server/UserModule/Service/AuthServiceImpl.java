package com.atman.server.UserModule.Service;

import com.atman.server.UserModule.DTO.UserCredentialResponse;
import com.atman.server.UserModule.DTO.UserLoginRequestDto;
import com.atman.server.UserModule.DTO.UserRegistrationRequestDto;
import com.atman.server.UserModule.DTO.UserResetPasswordRequestDto;
import com.atman.server.UserModule.Entity.UserEntity;
import com.atman.server.UserModule.Enum.AccountStatus;
import com.atman.server.UserModule.Exception.UserAlreadyExistsException;
import com.atman.server.UserModule.Exception.UserNotFoundException;
import com.atman.server.UserModule.Exception.UserPasswordMismatchException;
import com.atman.server.UserModule.Exception.UserSuspendedException;
import com.atman.server.UserModule.Mapper.UserMapper;
import com.atman.server.UserModule.Repository.UserRepository;
import com.atman.server.UserModule.Service.Impl.AuthService;
import com.atman.server.UserModule.Service.Impl.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@RequiredArgsConstructor
@Service
public class AuthServiceImpl implements AuthService {

    private final TokenService tokenService;
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserCredentialResponse registerUser(UserRegistrationRequestDto dto) {
        if(userRepository.existsByContactNumber(dto.getContactNumber())) {
            throw new UserAlreadyExistsException("User already exists");
        }

        UserEntity user = UserEntity.builder().username(dto.getUsername()).contactNumber(dto.getContactNumber()).password(passwordEncoder.encode(dto.getPassword())).userRole(dto.getUserRole()).accountStatus(AccountStatus.ACTIVATED).build();

        return generateCredentials(userRepository.save(user));
    }

    @Override
    public UserCredentialResponse loginUser(UserLoginRequestDto userLoginRequestDto) {

        UserEntity user = userRepository.findByContactNumber(userLoginRequestDto.getContactNumber()).orElseThrow(() -> new UserNotFoundException("User not found"));

        if(!passwordEncoder.matches(userLoginRequestDto.getPassword(), user.getPassword())) {
            throw new UserPasswordMismatchException("Invalid credentials");
        }

        if(user.getAccountStatus() == AccountStatus.SUSPENDED) {
            throw new UserSuspendedException("Account is suspended");
        }

        return generateCredentials(user);
    }

    @Override
    public UserCredentialResponse resetPassword(UserResetPasswordRequestDto userResetPasswordRequestDto) {
        UserEntity user = userRepository.findById(userResetPasswordRequestDto.getUserId()).orElseThrow(() -> new UserNotFoundException("User not found with id: " + userResetPasswordRequestDto.getUserId()));

        if(!passwordEncoder.matches(userResetPasswordRequestDto.getOldPassword(), user.getPassword())) {
            throw new UserPasswordMismatchException("Incorrect old password");
        }
        user.setPassword(passwordEncoder.encode(userResetPasswordRequestDto.getNewPassword()));
        return generateCredentials(userRepository.save(user));
    }

    private UserCredentialResponse generateCredentials(UserEntity user) {
        String accessToken = tokenService.generateAccessToken(user.getContactNumber());
        String refreshToken = tokenService.generateRefreshToken(user.getContactNumber());
        return userMapper.mapToUserCredentialResponseDto(user, accessToken, refreshToken);
    }
}
