package ru.otus.spring.volgin.service;

import ru.otus.spring.volgin.domain.Comment;
import ru.otus.spring.volgin.dto.CommentDto;

import java.util.List;

/**
 * Сервис для работы с комментариями
 */
public interface CommentService {

    /**
     * Сохраняет комментарий
     * @param comment комментарий
     * @param bookId  идентификатор книги
     * @return комментарий
     */
    CommentDto save(Comment comment, long bookId);

    /**
     * Обновляет комментарий
     * @param id   идентификатор записи
     * @param text текст комментария
     * @return комментарий
     */
    CommentDto update(long id, String text);

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
    CommentDto findById(long id);

    /**
     * Возвращает комментарии по идентификатору книги
     * @param id идентификатор книги
     * @return комментарии
     */
    List<CommentDto> findBookCommentsByBookId(long id);

    /**
     * Возвращает все комментарии
     * @return список всех комментариев
     */
    List<CommentDto> findAll();
}
