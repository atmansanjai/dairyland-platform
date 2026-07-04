package com.atman.server.UserModule.Mapper;

import com.atman.server.UserModule.DTO.*;
import com.atman.server.UserModule.Entity.UserEntity;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserCredentialResponse mapToUserCredentialResponseDto(UserEntity userEntity,String accessToken, String refreshToken);

    UserResponseDto mapToUserResponseDto(UserEntity userEntity);

}
