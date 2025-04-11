package com.patrick.games_api.mappers;

import com.patrick.games_api.dto.CategoriaRequestDto;
import com.patrick.games_api.dto.CategoriaResponseDto;
import com.patrick.games_api.domain.Categoria;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CategoriaMapper {

    Categoria toCategoria(CategoriaRequestDto categoriaRequestDto);

    CategoriaResponseDto toResponse(Categoria categoria);
}
