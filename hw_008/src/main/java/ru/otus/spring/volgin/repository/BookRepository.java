package ru.otus.spring.volgin.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import ru.otus.spring.volgin.domain.Book;

import java.util.List;

/**
 * Репозиторий для работы с книгами
 */
public interface BookRepository extends MongoRepository<Book, String> {

    /**
     * Возвращает список книг по названию жанра
     * @param genreName название жанра
     * @return список книг по названию жанра
     */
    @Query(fields = "{'comments': 0}")
    List<Book> findAllByGenreId(String genreName);
}
