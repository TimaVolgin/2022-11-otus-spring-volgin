package ru.otus.spring.volgin.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.Import;
import ru.otus.spring.volgin.domain.Book;
import ru.otus.spring.volgin.domain.Comment;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("Тест репозитория для работы с комментариями")
@DataMongoTest
@Import({CommentRepositoryImpl.class})
class CommentRepositoryTest {

    @Autowired
    private CommentRepositoryImpl commentRepository;
    @Autowired
    private BookRepository bookRepository;

    @DisplayName("Тестирует сохранение комментария")
    @Test
    void saveTest() {
        String expectedCommentText = "Удивительное произведение!";
        String existedBookId = "2";
        Comment newComment = commentRepository.saveComment(expectedCommentText, existedBookId);
        Optional<Book> book = bookRepository.findById(existedBookId);
        assertTrue(book.isPresent());
        assertEquals(expectedCommentText, newComment.getText());
        assertThat(book.get().getComments())
                .usingRecursiveFieldByFieldElementComparator()
                .contains(newComment);
    }

    @DisplayName("Тестирует обновление книги")
    @Test
    void updateTest() {
        Optional<Book> book = bookRepository.findById("2");
        assertTrue(book.isPresent());
        Optional<Comment> actualComment = book.get().getComments().stream().findAny();
        assertTrue(actualComment.isPresent());
        String expectedCommentText ="Удивительное произведение!";
        assertNotEquals(expectedCommentText, actualComment.get().getText(), "Отсутствуют вносимые изменения");
        var updatedCommentId = actualComment.get().getId();

        commentRepository.updateComment(updatedCommentId, expectedCommentText);


        book = bookRepository.findById("2");
        assertTrue(book.isPresent());
        Optional<Comment> updatedComment = book.get().getComments()
                .stream()
                .filter(comment -> comment.getId().equals(updatedCommentId))
                .findAny();
        assertTrue(updatedComment.isPresent());
        assertEquals(expectedCommentText, updatedComment.get().getText(), "Комментарий не совпадает");
    }

    @DisplayName("Тестирует удаление комментария")
    @Test
    void deleteByIdTest() {
        Optional<Book> bookBeforeDeleteComment = bookRepository.findById("3");
        assertTrue(bookBeforeDeleteComment.isPresent());
        var anyComment = bookBeforeDeleteComment.get().getComments().stream().findAny();
        assertTrue(anyComment.isPresent());
        var commentIdToDelete = anyComment.get().getId();

        commentRepository.deleteCommentById(commentIdToDelete);

        Optional<Book> bookAfterDeleteComment = bookRepository.findById("3");
        assertTrue(bookAfterDeleteComment.isPresent());
        assertEquals(bookBeforeDeleteComment.get().getComments().size() - 1, bookAfterDeleteComment.get().getComments().size(), "Комментарий не удалён");
        assertFalse(bookAfterDeleteComment.get().getComments()
                .stream()
                .anyMatch(comment -> comment.getId().equals(commentIdToDelete)), "Комментарий не удалён");
    }
}
