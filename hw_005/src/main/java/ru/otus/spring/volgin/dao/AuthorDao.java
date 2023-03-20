package ru.otus.spring.volgin.dao;

import ru.otus.spring.volgin.domain.Author;

import java.util.List;
import java.util.Optional;

/**
 * DAO по работе с авторами
 */
public interface AuthorDao {

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
