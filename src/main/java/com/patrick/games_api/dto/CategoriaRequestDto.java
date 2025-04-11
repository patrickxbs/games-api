package com.patrick.games_api.dto;

import jakarta.validation.constraints.NotBlank;

public record CategoriaRequestDto(@NotBlank String nome) {
}
