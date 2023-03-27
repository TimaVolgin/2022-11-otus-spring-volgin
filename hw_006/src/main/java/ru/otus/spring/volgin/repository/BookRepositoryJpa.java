package ru.otus.spring.volgin.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.otus.spring.volgin.domain.Book;

import javax.persistence.EntityGraph;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static java.util.Optional.ofNullable;

/**
 * Репозиторий для работы с книгами через JPA
 */
@Component
@RequiredArgsConstructor
public class BookRepositoryJpa implements BookRepository {

    @PersistenceContext
    private final EntityManager em;

    @Override
    public Book saveOrUpdate(Book book) {
        if (book.getId() == null) {
            em.persist(book);
            return book;
        }
        return em.merge(book);
    }

    @Override
    public void deleteById(long id) {
        em.remove(em.find(Book.class, id));
    }

    @Override
    public Optional<Book> findById(long id) {
        EntityGraph<?> entityGraph = em.getEntityGraph("book");
        return ofNullable(em.find(Book.class, id, Map.of("javax.persistence.fetchgraph", entityGraph)));
    }

    @Override
    public List<Book> findAll() {
        return em.createQuery("select b from Book b join fetch b.genre g join fetch b.author a", Book.class)
                .getResultList();
    }
}
