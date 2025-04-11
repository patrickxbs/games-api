package com.patrick.games_api.controllers;

import com.patrick.games_api.domain.User;
import com.patrick.games_api.dto.LoginRequestDto;
import com.patrick.games_api.dto.LoginResponseDto;
import com.patrick.games_api.infra.security.TokenService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "autenticacao", description = "Autenticar usuários na aplicação")
@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
@Log4j2
public class AuthenticationController {

    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;

    @PostMapping("/login")
    @Operation(summary = "Gera um token JWT",
            description = "Ao gerar o token, o usuário terá acesso aos recursos da api")
    public ResponseEntity<LoginResponseDto> login(@RequestBody @Valid LoginRequestDto loginRequestDto) {
        var usernamePassword = new UsernamePasswordAuthenticationToken(loginRequestDto.email(), loginRequestDto.senha());
        var auth = this.authenticationManager.authenticate(usernamePassword);

        var token = tokenService.generateToken((User) auth.getPrincipal());
        return ResponseEntity.ok(new LoginResponseDto(token));
    }

}
