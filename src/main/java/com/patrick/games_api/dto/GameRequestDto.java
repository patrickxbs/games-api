package com.patrick.games_api.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record GameRequestDto(@NotBlank String nome, @NotBlank String descricao,
                             @Min(1) double preco, @NotNull Long categoriaId) {
}
