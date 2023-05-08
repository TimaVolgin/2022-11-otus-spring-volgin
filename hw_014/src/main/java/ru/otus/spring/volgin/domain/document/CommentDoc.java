package ru.otus.spring.volgin.domain.document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Комментарий для книги
 */
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "comments")
public class CommentDoc {

    /** Идентификатор */
    @Id
    private String id;

    /** Комментарий */
    private String text;

    /**
     * Конструктор
     * @param text текст комментария
     */
    public CommentDoc(String text) {
        this.id = new ObjectId().toString();
        this.text = text;
    }
}
