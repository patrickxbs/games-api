package com.patrick.games_api.mappers;

import com.patrick.games_api.domain.User;
import com.patrick.games_api.dto.UserRequestDto;
import com.patrick.games_api.dto.UserResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMapper {

    User toUser(UserRequestDto userRequestDto);

    UserResponseDto toDto(User user);
}
