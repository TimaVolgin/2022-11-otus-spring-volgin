package ru.otus.spring.volgin.service;

import ru.otus.spring.volgin.domain.Genre;

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
    Genre save(String name);

    /**
     * Возвращает все жанры
     * @return список всех жанров
     */
    List<Genre> findAll();

    /**
     * Возвращает жанр по названию содержащему текст
     * @param text текст
     * @return жанр
     */
    List<Genre> findByNameContainingText(String text);

    /**
     * Возвращает жанр по идентификатору
     * @param id идентификатор жанра
     * @return жанр
     */
    Genre findById(String id);
}
