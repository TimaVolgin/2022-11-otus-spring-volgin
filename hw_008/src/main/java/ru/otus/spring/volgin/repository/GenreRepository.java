package ru.otus.spring.volgin.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.spring.volgin.domain.Genre;

import java.util.List;

/**
 * Репозиторий для работы с жанрами
 */
public interface GenreRepository extends MongoRepository<Genre, String> {

    /**
     * Возвращает жанр по названию содержащему текст
     * @param text название
     * @return жанр
     */
    List<Genre> findByNameLike(String text);
}
