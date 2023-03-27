package ru.otus.spring.volgin.repository;

import ru.otus.spring.volgin.domain.Author;

import java.util.List;
import java.util.Optional;

/**
 * Репозиторий для работы с авторами
 */
public interface AuthorRepository {

    /**
     * Сохраняет автора
     * @param author автор
     * @return автор
     */
    Author save(Author author);

    /**
     * Возвращает всех авторов
     * @return список всех авторов
     */
    List<Author> findAll();

    /**
     * Возвращает автора по идентификатору
     * @param id идентификатор автора
     * @return автор
     */
    Optional<Author> findById(long id);
}
