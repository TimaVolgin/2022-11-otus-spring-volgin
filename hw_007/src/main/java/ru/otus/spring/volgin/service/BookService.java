package ru.otus.spring.volgin.service;

import ru.otus.spring.volgin.domain.Book;

import java.util.List;

/**
 * Контракт для работы с книгами
 */
public interface BookService {

    /**
     * Сохраняет книгу
     * @param title     название книги
     * @param published дата публикации
     * @param genreId   идентификатор жанра
     * @param authorId  идентификатор автора
     * @return книга
     */
    Book save(String title, String published, long genreId, long authorId);

    /**
     * Обновляет книгу
     * @param id        идентификатор записи
     * @param title     название книги
     * @param published дата публикации
     * @param genreId   идентификатор жанра
     * @param authorId  идентификатор автора
     * @return книга
     */
    Book update(long id, String title, String published, long genreId, long authorId);

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
    Book findById(long id);

    /**
     * Возвращает все книги
     * @return список всех книг
     */
    List<Book> findAll();

    /**
     * Возвращает все книги по названию жанра
     * @param genreName название жанра
     * @return список всех книг по названию жанра
     */
    List<Book> findAllByGenreName(String genreName);
}
