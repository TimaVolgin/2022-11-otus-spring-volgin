package ru.otus.spring.volgin.repository;

import ru.otus.spring.volgin.domain.Book;

import java.util.List;
import java.util.Optional;

/**
 * Репозиторий для работы с книгами
 */
public interface BookRepository {

    /**
     * Сохраняет или обновляет книгу
     * @param book книга
     * @return книга
     */
    Book saveOrUpdate(Book book);

    /**
     * Удаляет книгу по идентификатору
     * @param id идентификатор книги
     */
    void deleteById(long id);

    /**
     * Возвращает книгу по идентификатору
     * @param id идентификатор книги
     * @return книга
     */
    Optional<Book> findById(long id);

    /**
     * Возвращает все книги
     * @return список всех книг
     */
    List<Book> findAll();
}
