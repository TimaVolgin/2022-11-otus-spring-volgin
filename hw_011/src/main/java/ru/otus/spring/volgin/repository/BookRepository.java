package ru.otus.spring.volgin.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;
import ru.otus.spring.volgin.domain.Book;

/**
 * Репозиторий для работы с книгами
 */
public interface BookRepository extends ReactiveMongoRepository<Book, String> {

    /**
     * Возвращает список книг
     * @return список всех книг
     */
    @Query(fields = "{'comments': 0}", value = "{}")
    Flux<Book> findAll(Pageable page);
}
