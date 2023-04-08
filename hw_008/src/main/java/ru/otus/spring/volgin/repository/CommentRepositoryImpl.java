package ru.otus.spring.volgin.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;
import ru.otus.spring.volgin.domain.Book;
import ru.otus.spring.volgin.domain.Comment;

import static java.text.MessageFormat.format;

@Component
@RequiredArgsConstructor
public class CommentRepositoryImpl implements CommentRepository {

    private final MongoTemplate mt;

    @Override
    public Comment saveComment(String text, String bookId) {
        Query query = new Query().addCriteria(Criteria.where("id").is(bookId));
        Comment comment = new Comment(text);
        Update update = new Update().push("comments", comment);
        if (mt.findAndModify(query, update, Book.class) == null) {
            throw new IllegalArgumentException(format("Не найдена книга с идентификатором {0}", bookId));
        }
        return comment;
    }

    @Override
    public Comment updateComment(String commentId, String text) {
        Book book = mt.findOne(Query.query(Criteria.where("comments._id").is(commentId)), Book.class);
        if (book != null) {
            Comment commentToUpdate = book.getComments()
                    .stream()
                    .filter(c -> c.getId().equals(commentId))
                    .findFirst()
                    .orElse(null);
            if (commentToUpdate != null) {
                commentToUpdate.setText(text);
                mt.save(book);
                return commentToUpdate;
            } else {
                throw new IllegalArgumentException(format("Не найден комментарий с идентификатором {0}", commentId));
            }
        } else {
            throw new IllegalArgumentException(format("Не найдена книга, которая содержит комментарий с идентификатором {0}", commentId));
        }
    }

    @Override
    public void deleteCommentById(String commentId) {
        Query query = new Query().addCriteria(Criteria.where("comments.id").is(commentId));
        Update update = new Update().pull("comments", Query.query(Criteria.where("id").is(commentId)));
        mt.findAndModify(query, update, Book.class);
    }
}
