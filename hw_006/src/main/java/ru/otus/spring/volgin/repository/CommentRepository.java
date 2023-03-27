package ru.otus.spring.volgin.repository;

import ru.otus.spring.volgin.domain.Comment;

import java.util.List;
import java.util.Optional;

/**
 * Репозиторий для работы с комментариями для книги
 */
public interface CommentRepository {

    /**
     * Сохраняет или обновляет комментарий
     * @param comment комментарий
     * @return комментарий
     */
    Comment saveOrUpdate(Comment comment);

    /**
     * Возвращает все жанры
     * @return список всех жанров
     */
    List<Comment> findAll();

    /**
     * Возвращает комментарий по идентификатору
     * @param id идентификатор комментария
     * @return комментарий
     */
    Optional<Comment> findById(long id);

    /**
     * Удаляет комментарий по идентификатору
     * @param id идентификатор комментария
     */
    void deleteById(long id);
}
