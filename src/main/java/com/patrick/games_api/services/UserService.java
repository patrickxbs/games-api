package com.patrick.games_api.services;

import com.patrick.games_api.domain.User;
import com.patrick.games_api.exception.BadRequestException;
import com.patrick.games_api.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public User save(User user) {

        if (userRepository.findByEmail(user.getEmail()) != null)
            throw new BadRequestException("Usuario já cadastrado");

        user.setSenha(passwordEncoder.encode(user.getSenha()));
        return userRepository.save(user);
    }
}
