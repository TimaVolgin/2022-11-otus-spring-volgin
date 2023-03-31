package ru.otus.spring.volgin.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Жанр
 */
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "genres")
public class Genre {

    /** Идентификатор */
    @Id
    private String id;

    /** Название жанра */
    private String name;
}
