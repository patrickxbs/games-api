package com.patrick.games_api.services;

import com.patrick.games_api.exception.BadRequestException;
import com.patrick.games_api.domain.Categoria;
import com.patrick.games_api.repositories.CategoriaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class CategoriaService {

    private final CategoriaRepository categoriaRepository;

    @Transactional
    public Categoria create(Categoria categoria) {
        try {
            return categoriaRepository.save(categoria);
        } catch (DataIntegrityViolationException ex) {
            throw new BadRequestException(String.format("A categoria '%s' já existe", categoria.getNome()));
        }
    }

    @Transactional(readOnly = true)
    public Categoria findById(long id) {
        return categoriaRepository.findById(id).orElseThrow(
                () -> new BadRequestException(String.format("Categoria com id '%s' não encontrado", id))
        );
    }

    @Transactional(readOnly = true)
    public Page<Categoria> getAll(Pageable pageable) {
        return categoriaRepository.findAll(pageable);
    }

    @Transactional
    public void replace(long id, Categoria categoria) {
        Categoria newCategoria = findById(id);
        newCategoria.setNome(categoria.getNome());
    }

    @Transactional
    public void delete(long id) {
        Categoria categoria = findById(id);
        categoriaRepository.delete(categoria);
    }
}
