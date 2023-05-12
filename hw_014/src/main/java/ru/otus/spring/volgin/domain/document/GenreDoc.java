package ru.otus.spring.volgin.domain.document;

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
public class GenreDoc {

    /** Идентификатор */
    @Id
    private String id;

    /** Идентификатор до миграции */
    private Long previousId;

    /** Название жанра */
    private String name;
}
