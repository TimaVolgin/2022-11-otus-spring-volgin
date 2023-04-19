package ru.otus.spring.volgin.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import ru.otus.spring.volgin.dto.GenreDto;
import ru.otus.spring.volgin.mapper.GenreMapper;
import ru.otus.spring.volgin.repository.GenreRepository;

/**
 * Контроллер работы с жанрами
 */
@RestController
@RequiredArgsConstructor
public class GenreController {

    /** Репозиторий по работе с жанрами */
    private final GenreRepository genreRepository;
    /** Маппер для работы с жанрами */
    private final GenreMapper genreMapper;

    /**
     * Возвращает список жанров
     * @return жанры
     */
    @GetMapping("/genres")
    public Flux<GenreDto> getGenres() {
        return genreRepository.findAll()
                .map(genreMapper::toDto);
    }
}
