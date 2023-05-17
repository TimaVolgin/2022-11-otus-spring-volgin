package ru.otus.spring.volgin.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.spring.volgin.domain.Comment;

/**
 * Репозиторий для работы с комментариями для книги
 */
public interface CommentRepository extends JpaRepository<Comment, Long> {
}
