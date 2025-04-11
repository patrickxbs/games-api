package com.patrick.games_api.controllers;

import com.patrick.games_api.dto.GameRequestDto;
import com.patrick.games_api.dto.GameResponseDto;
import com.patrick.games_api.mappers.GameMapper;
import com.patrick.games_api.domain.Game;
import com.patrick.games_api.services.GameService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

import static io.swagger.v3.oas.annotations.enums.ParameterIn.QUERY;

@Tag(name = "games", description = "CRUD Game")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/games")
public class GameController {

    private final GameService gameService;
    private final GameMapper gameMapper;


    @PostMapping
    @Operation(summary = "Criar um novo jogo",
            security = @SecurityRequirement(name = "security"))
    public ResponseEntity<GameResponseDto> create(@RequestBody @Valid GameRequestDto gameRequestDto) {
        Game game = gameService.create(gameMapper.toGame(gameRequestDto));
        GameResponseDto gameResponse = gameMapper.toResponse(game);

        URI local = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/api/v1/games/{id}")
                .buildAndExpand(game.getId())
                .toUri();

        return ResponseEntity.created(local).body(gameResponse);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar um jogo por ID",
            security = @SecurityRequirement(name = "security"))
    public ResponseEntity<GameResponseDto> getId(@PathVariable Long id) {
        Game game = gameService.findById(id);
        return ResponseEntity.ok(gameMapper.toResponse(game));
    }

    @GetMapping
    @Operation(summary = "Retorna todos os jogos ", description = "Em formato de paginação",
            security = @SecurityRequirement(name = "security"),
            parameters = {
                    @Parameter(in = QUERY, name = "page",
                            content = @Content(schema = @Schema(type = "integer", defaultValue = "0")),
                            description = "Página a ser retornada"
                    ),
                    @Parameter(in = QUERY, name = "size",
                            content = @Content(schema = @Schema(type = "integer", defaultValue = "20")),
                            description = "Total de elementos por página"
                    ),
                    @Parameter(in = QUERY, name = "sort", hidden = true,
                            array = @ArraySchema(schema = @Schema(type = "string", defaultValue = "nome,asc")),
                            description = "Ordena os elementos")
            })
    public ResponseEntity<Page<GameResponseDto>> getAll(@Parameter(hidden = true)
                         @PageableDefault(size = 5, sort = {"nome"}) Pageable pageable){
        Page<Game> games = gameService.findAll(pageable);
        Page<GameResponseDto> gamesResponsePage = games.map(gameMapper::toResponse);
        return ResponseEntity.ok(gamesResponsePage);
    }

    @GetMapping("/categoria/{id}")
    @Operation(summary = "Retorna todos os jogos por categoria", description = "Em formato de paginação",
            security = @SecurityRequirement(name = "security"),
            parameters = {
                    @Parameter(in = QUERY, name = "page",
                            content = @Content(schema = @Schema(type = "integer", defaultValue = "0")),
                            description = "Página a ser retornada"
                    ),
                    @Parameter(in = QUERY, name = "size",
                            content = @Content(schema = @Schema(type = "integer", defaultValue = "20")),
                            description = "Total de elementos por página"
                    ),
                    @Parameter(in = QUERY, name = "sort", hidden = true,
                            array = @ArraySchema(schema = @Schema(type = "string", defaultValue = "nome,asc")),
                            description = "Ordena os elementos")
            })
    public ResponseEntity<Page<GameResponseDto>> getAllByCategoriaId(@Parameter(hidden = true)
                         @PageableDefault(size = 5, sort = {"nome"}) Pageable pageable, @PathVariable Long id) {
        Page<Game> gamesByCategoria = gameService.findAllByCategoria(pageable, id);
        Page<GameResponseDto> gamesResponsePage = gamesByCategoria.map(gameMapper::toResponse);
        return ResponseEntity.ok(gamesResponsePage);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualiza um jogo por id",
            security = @SecurityRequirement(name = "security"))
    public ResponseEntity<Void> update(@PathVariable Long id, @RequestBody @Valid GameRequestDto gameRequestDto) {
        Game game = gameMapper.toGame(gameRequestDto);
        gameService.replace(id, game);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deleta um jogo por ID",
            security = @SecurityRequirement(name = "security"))
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        gameService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
