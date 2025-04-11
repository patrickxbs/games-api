package com.patrick.games_api.dto;

public record GameResponseDto(Long id, String nome, double preco, String descricao, Long categoriaId) {
}
