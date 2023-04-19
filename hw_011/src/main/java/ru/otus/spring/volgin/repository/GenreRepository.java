package ru.otus.spring.volgin.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import ru.otus.spring.volgin.domain.Genre;

/**
 * Репозиторий для работы с жанрами
 */
public interface GenreRepository extends ReactiveMongoRepository<Genre, String> {
}
