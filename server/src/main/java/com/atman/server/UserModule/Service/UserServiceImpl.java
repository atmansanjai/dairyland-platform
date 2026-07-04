package com.atman.server.UserModule.Service;

import com.atman.server.UserModule.DTO.UserAccountStatusRequestDto;
import com.atman.server.UserModule.DTO.UserDeletionRequestDto;
import com.atman.server.UserModule.DTO.UserProfileUpdationRequestDto;
import com.atman.server.UserModule.DTO.UserResponseDto;
import com.atman.server.UserModule.Entity.UserEntity;
import com.atman.server.UserModule.Exception.UserNotFoundException;
import com.atman.server.UserModule.Mapper.UserMapper;
import com.atman.server.UserModule.Repository.UserRepository;
import com.atman.server.UserModule.Service.Impl.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;


@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public UserResponseDto updateUserDetails(UserProfileUpdationRequestDto userProfileUpdationRequestDto) {
        UserEntity user = findUserOrThrow(userProfileUpdationRequestDto.getUserId());
        user.setUsername(userProfileUpdationRequestDto.getUsername());
        return userMapper.mapToUserResponseDto(userRepository.save(user));
    }

    @Override
    public UUID deleteUser(UserDeletionRequestDto userDeletionRequestDto) {
        if(!userRepository.existsById(userDeletionRequestDto.getUserId())) {
            throw new UserNotFoundException("User not found");
        }
        userRepository.deleteById(userDeletionRequestDto.getUserId());
        return userDeletionRequestDto.getUserId();
    }

    @Override
    public UserResponseDto updateAccountStatus(UserAccountStatusRequestDto userAccountStatusRequestDto) {
        UserEntity user = findUserOrThrow(userAccountStatusRequestDto.getUserId());
        user.setAccountStatus(userAccountStatusRequestDto.getAccountStatus());
        return userMapper.mapToUserResponseDto(userRepository.save(user));
    }

    private UserEntity findUserOrThrow(UUID userId) {
        return userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("User not found with id: " + userId));
    }
}
