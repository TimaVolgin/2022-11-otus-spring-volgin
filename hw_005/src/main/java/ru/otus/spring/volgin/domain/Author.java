package ru.otus.spring.volgin.domain;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

/**
 * Книжный автор
 */
@Builder
@Data
public class Author {
    /**
     * Идентификатор
     */
    private Long id;
    /**
     * ФИО
     */
    private String fio;
    /**
     * Дата рождения
     */
    private LocalDate birthday;
}
