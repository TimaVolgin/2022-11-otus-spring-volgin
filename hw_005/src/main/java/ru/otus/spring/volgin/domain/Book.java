package ru.otus.spring.volgin.domain;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

/**
 * Книга
 */
@Builder
@Data
public class Book {
    /**
     * Идентификатор
     */
    private Long id;
    /**
     * Название книги
     */
    private String title;
    /**
     * Дата публикации книги
     */
    private LocalDate published;
    /**
     * Жанр
     */
    private Genre genre;
    /**
     * Автор
     */
    private Author author;
}
