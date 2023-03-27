package ru.otus.spring.volgin.service;

import ru.otus.spring.volgin.domain.Comment;

import java.util.List;

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
    Comment save(String text, long bookId);

    /**
     * Обновляет комментарий
     * @param id   идентификатор записи
     * @param text текст комментария
     * @return комментарий
     */
    Comment update(long id, String text);

    /**
     * Удаляет комментарий по идентификатору
     * @param id идентификатор комментария
     */
    void deleteById(long id);

    /**
     * Возвращает комментарий по идентификатору
     * @param id идентификатор комментария
     * @return комментарий
     */
    Comment findById(long id);

    /**
     * Возвращает комментарии по идентификатору книги
     * @param id идентификатор книги
     * @return комментарии
     */
    List<Comment> findBookCommentsByBookId(long id);

    /**
     * Возвращает все комментарии
     * @return список всех комментариев
     */
    List<Comment> findAll();
}
