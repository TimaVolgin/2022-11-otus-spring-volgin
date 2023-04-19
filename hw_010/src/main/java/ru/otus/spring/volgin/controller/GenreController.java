package ru.otus.spring.volgin.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.spring.volgin.dto.GenreDto;
import ru.otus.spring.volgin.service.GenreService;

import java.util.List;

/**
 * Контроллер работы с жанрами
 */
@RestController
@RequiredArgsConstructor
public class GenreController {

    /** Сервис работы с жанрами */
    private final GenreService genreService;

    /**
     * Возвращает список жанров
     * @return жанры
     */
    @GetMapping("/genres")
    public List<GenreDto> getGenres() {
        return genreService.findAll();
    }
}
