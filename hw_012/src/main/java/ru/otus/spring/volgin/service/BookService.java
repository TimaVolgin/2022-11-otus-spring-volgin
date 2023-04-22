package ru.otus.spring.volgin.service;

import ru.otus.spring.volgin.dto.BookDto;
import ru.otus.spring.volgin.dto.BookWithCommentsDto;

import java.util.List;

/**
 * Контракт для работы с книгами
 */
public interface BookService {

    /**
     * Сохраняет книгу
     * @param book сохраняемая книга
     * @return книга
     */
    BookDto saveOrUpdate(BookDto book);

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
    BookWithCommentsDto findById(long id);

    /**
     * Возвращает все книги
     * @return список всех книг
     */
    List<BookDto> findAll();
}
