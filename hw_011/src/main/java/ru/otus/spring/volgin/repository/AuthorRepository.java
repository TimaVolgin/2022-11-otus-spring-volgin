package ru.otus.spring.volgin.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import ru.otus.spring.volgin.domain.Author;

/**
 * Репозиторий для работы с авторами
 */
public interface AuthorRepository extends ReactiveMongoRepository<Author, String> {
}
