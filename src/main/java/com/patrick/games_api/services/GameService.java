package com.patrick.games_api.services;

import com.patrick.games_api.exception.BadRequestException;
import com.patrick.games_api.domain.Categoria;
import com.patrick.games_api.domain.Game;
import com.patrick.games_api.repositories.GameRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class GameService {

    private final GameRepository gameRepository;
    private final CategoriaService categoriaService;

    @Transactional
    public Game create(Game game) {

        if (gameRepository.findByNome(game.getNome()) != null)
            throw new BadRequestException(String.format("O jogo '%s' já cadastrado", game.getNome()));

        Categoria categoria = categoriaService.findById(game.getCategoria().getId());
        game.setCategoria(categoria);
        return gameRepository.save(game);
    }

    @Transactional(readOnly = true)
    public Game findById(long id) {
        return gameRepository.findById(id).orElseThrow(
                () -> new BadRequestException(String.format("Jogo com id = '%s' não encontrado", id))
        );
    }

    @Transactional(readOnly = true)
    public Page<Game> findAll(Pageable pageable) {
        return gameRepository.findAll(pageable);
    }

    @Transactional(readOnly = true)
    public Page<Game> findAllByCategoria(Pageable pageable, Long categoriaId) {
        return gameRepository.findAllByCategoriaId(pageable, categoriaId);
    }

    @Transactional
    public void replace(long id, Game game) {
        Game gameReplace = findById(id);
        gameReplace.setNome(game.getNome());
        gameReplace.setPreco(game.getPreco());
        gameReplace.setDescricao(game.getDescricao());
        gameReplace.setCategoria(game.getCategoria());
    }

    @Transactional
    public void delete(Long id) {
        Game game = findById(id);
        gameRepository.delete(game);
    }

}
