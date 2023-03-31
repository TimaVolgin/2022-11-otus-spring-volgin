package ru.otus.spring.volgin.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.spring.volgin.domain.Author;

/**
 * Репозиторий для работы с авторами
 */
public interface AuthorRepository extends MongoRepository<Author, String> {
}
