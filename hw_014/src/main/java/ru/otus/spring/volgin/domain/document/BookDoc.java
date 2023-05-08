package ru.otus.spring.volgin.domain.document;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * Книга
 */
@Builder
@Data
@ToString(exclude = "comments")
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "books")
public class BookDoc {

    /** Идентификатор */
    @Id
    private String id;

    /** Название книги */
    private String title;

    /** Дата публикации книги */
    private LocalDate published;

    /** Жанр */
    @DBRef
    private GenreDoc genre;

    /** Автор */
    @DBRef
    private AuthorDoc author;

    private Set<CommentDoc> comments = new HashSet<>();
}
