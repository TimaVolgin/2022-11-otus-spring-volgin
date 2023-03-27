package ru.otus.spring.volgin.repository;

import ru.otus.spring.volgin.domain.Genre;

import java.util.List;
import java.util.Optional;

/**
 * Репозиторий для работы с жанрами
 */
public interface GenreRepository {

    /**
     * Сохраняет жанр
     * @param genre жанр
     * @return жанр
     */
    Genre save(Genre genre);

    /**
     * Возвращает все жанры
     * @return список всех жанров
     */
    List<Genre> findAll();

    /**
     * Возвращает жанр по названию
     * @param name название
     * @return жанр
     */
    Optional<Genre> findByName(String name);

    /**
     * Возвращает жанр по идентификатору
     * @param id идентификатор жанра
     * @return жанр
     */
    Optional<Genre> findById(long id);
}
