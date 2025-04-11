package com.patrick.games_api.controllers;


import com.patrick.games_api.domain.User;
import com.patrick.games_api.dto.UserRequestDto;
import com.patrick.games_api.dto.UserResponseDto;
import com.patrick.games_api.mappers.UserMapper;
import com.patrick.games_api.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "registro", description = "Cria um novo usuario")
@RequiredArgsConstructor
@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;

    @PostMapping("/registro")
    @Operation(summary = "Cria um novo usuario",
            description = "Ao criar um usuário, poderá fazer a autenticação")
    public ResponseEntity<UserResponseDto> registro(@RequestBody @Valid UserRequestDto UserRequestDto) {
        User newUser = userService.save(userMapper.toUser(UserRequestDto));
        UserResponseDto userResponse = userMapper.toDto(newUser);
        return ResponseEntity.status(HttpStatus.CREATED).body(userResponse);
    }
}
