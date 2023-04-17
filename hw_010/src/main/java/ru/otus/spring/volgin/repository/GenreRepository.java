package ru.otus.spring.volgin.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.spring.volgin.domain.Genre;

import java.util.Optional;

/**
 * Репозиторий для работы с жанрами
 */
public interface GenreRepository extends JpaRepository<Genre, Long> {

    /**
     * Возвращает жанр по названию
     * @param name название
     * @return жанр
     */
    Optional<Genre> findByName(String name);
}
