package ru.otus.spring.volgin.service;

import ru.otus.spring.volgin.domain.Comment;

import java.util.Set;

/**
 * Сервис для работы с комментариями
 */
public interface CommentService {

    /**
     * Сохраняет комментарий
     * @param text   текст комментария
     * @param bookId идентификатор книги
     * @return комментарий
     */
    Comment save(String text, String bookId);

    /**
     * Обновляет комментарий
     * @param id   идентификатор записи
     * @param text текст комментария
     * @return комментарий
     */
    Comment update(String id, String text);

    /**
     * Удаляет комментарий по идентификатору
     * @param id идентификатор комментария
     */
    void deleteById(String id);

    /**
     * Возвращает комментарии по идентификатору книги
     * @param id идентификатор книги
     * @return комментарии к книге
     */
    Set<Comment> findByBookId(String id);

    /**
     * Возвращает все комментарии
     * @return список всех комментариев
     */
    Set<Comment> findAll();
}
