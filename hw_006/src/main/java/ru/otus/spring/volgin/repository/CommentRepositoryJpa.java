package ru.otus.spring.volgin.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.otus.spring.volgin.domain.Comment;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

import static java.util.Optional.ofNullable;

/**
 * Репозиторий для работы с комментариями книг через JPA
 */
@Component
@RequiredArgsConstructor
public class CommentRepositoryJpa implements CommentRepository {

    @PersistenceContext
    private final EntityManager em;

    @Override
    public Comment saveOrUpdate(Comment bookComment) {
        if (bookComment.getId() == null) {
            em.persist(bookComment);
            return bookComment;
        }
        return em.merge(bookComment);
    }

    @Override
    public void deleteById(long id) {
        em.remove(em.find(Comment.class, id));
    }

    @Override
    public Optional<Comment> findById(long id) {
        return ofNullable(em.find(Comment.class, id));
    }

    @Override
    public List<Comment> findAll() {
        return em.createQuery("select c from Comment c", Comment.class).getResultList();
    }
}
