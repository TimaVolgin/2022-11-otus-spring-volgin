package ru.otus.spring.volgin.repository;

import ru.otus.spring.volgin.domain.Comment;

/**
 * Репозиторий для работы с комментариями для книги
 */
public interface CommentRepository {

    /**
     * Сохраняет комментарий
     * @param text   текст комментария
     * @param bookId идентификатор книги
     * @return комментарий
     */
    Comment saveComment(String text, String bookId);

    /**
     * Обновляет комментарий
     * @param commentId идентификатор комментария
     * @param text      текст комментария
     * @return комментарий
     */
    Comment updateComment(String commentId, String text);

    /**
     * Удаляет комментарий по идентификатору
     * @param commentId идентификатор комментария
     */
    void deleteCommentById(String commentId);
}
