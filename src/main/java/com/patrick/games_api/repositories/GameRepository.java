package com.patrick.games_api.repositories;

import com.patrick.games_api.domain.Game;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GameRepository extends JpaRepository<Game, Long> {

    Page<Game> findAllByCategoriaId(Pageable pageable, Long categoriaId);

    Game findByNome(String nome);
}
