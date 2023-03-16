package ru.otus.spring.volgin.domain;

import lombok.Builder;
import lombok.Data;

/**
 * Жанр
 */
@Builder
@Data
public class Genre {
    /**
     * Идентификатор
     */
    private Long id;
    /**
     * Название жанра
     */
    private String name;
}
