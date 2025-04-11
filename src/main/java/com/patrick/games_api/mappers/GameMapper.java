package com.patrick.games_api.mappers;

import com.patrick.games_api.dto.GameRequestDto;
import com.patrick.games_api.dto.GameResponseDto;
import com.patrick.games_api.domain.Game;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface GameMapper {

    @Mapping(source = "categoriaId", target = "categoria.id")
    Game toGame(GameRequestDto gameRequestDto);

    @Mapping(source = "categoria.id", target = "categoriaId")
    GameResponseDto toResponse(Game game);
}
