package com.patrick.games_api.controllers;

import com.patrick.games_api.dto.CategoriaRequestDto;
import com.patrick.games_api.dto.CategoriaResponseDto;
import com.patrick.games_api.mappers.CategoriaMapper;
import com.patrick.games_api.domain.Categoria;
import com.patrick.games_api.services.CategoriaService;
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

@Tag(name = "categorias", description = "CRUD Categoria")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/categorias")
public class CategoriaController {

    private final CategoriaService categoriaService;
    private final CategoriaMapper categoriaMapper;

    @PostMapping
    @Operation(summary = "Cria uma nova categoria",
            security = @SecurityRequirement(name = "security"))
    public ResponseEntity<CategoriaResponseDto> create(@RequestBody @Valid CategoriaRequestDto categoriaRequestDto) {
        Categoria categoria = categoriaMapper.toCategoria(categoriaRequestDto);
        categoriaService.create(categoria);
        CategoriaResponseDto categoriaResponse = categoriaMapper.toResponse(categoria);

        URI local = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/api/v1/categorias/{id}")
                .buildAndExpand(categoria.getId())
                .toUri();

        return ResponseEntity.created(local).body(categoriaResponse);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar uma categoria por ID",
            security = @SecurityRequirement(name = "security"))
    public ResponseEntity<CategoriaResponseDto> getId(@PathVariable long id) {
        Categoria categoria = categoriaService.findById(id);
        CategoriaResponseDto categoriaResponseDto = categoriaMapper.toResponse(categoria);
        return ResponseEntity.ok(categoriaResponseDto);
    }

    @GetMapping
    @Operation(summary = "Retorna todos as categorias ", description = "Em formato de paginação",
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
    public ResponseEntity<Page<CategoriaResponseDto>> getAll(@Parameter(hidden = true)
                         @PageableDefault(size = 5, sort = {"nome"}) Pageable pageable) {
        Page<Categoria> categorias = categoriaService.getAll(pageable);
        Page<CategoriaResponseDto> categoriasResponse = categorias.map(categoriaMapper::toResponse);
        return ResponseEntity.ok(categoriasResponse);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualiza uma categoria por id",
            security = @SecurityRequirement(name = "security"))
    public ResponseEntity<Void> update(@PathVariable long id,
                                            @RequestBody @Valid CategoriaRequestDto categoriaRequestDto) {
        Categoria categoria = categoriaMapper.toCategoria(categoriaRequestDto);
        categoriaService.replace(id, categoria);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deleta uma categoria por ID",
            security = @SecurityRequirement(name = "security"))
    public ResponseEntity<Void> delete(@PathVariable long id) {
        categoriaService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
