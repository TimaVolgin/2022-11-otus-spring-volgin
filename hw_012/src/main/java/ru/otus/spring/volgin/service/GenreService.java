package ru.otus.spring.volgin.service;

import ru.otus.spring.volgin.domain.Genre;
import ru.otus.spring.volgin.dto.GenreDto;

import java.util.List;

/**
 * Контракт для работы с жанрами
 */
public interface GenreService {

    /**
     * Сохраняет жанр
     * @param name название жанра
     * @return жанр
     */
    GenreDto save(String name);

    /**
     * Возвращает все жанры
     * @return список всех жанров
     */
    List<GenreDto> findAll();

    /**
     * Возвращает жанр по названию
     * @param name название
     * @return жанр
     */
    Genre findByName(String name);

    /**
     * Возвращает жанр по идентификатору
     * @param id идентификатор жанра
     * @return жанр
     */
    Genre findById(long id);
}
