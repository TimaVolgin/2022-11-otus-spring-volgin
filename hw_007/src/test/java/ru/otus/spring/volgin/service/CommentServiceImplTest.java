package ru.otus.spring.volgin.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.spring.volgin.domain.Book;
import ru.otus.spring.volgin.domain.Comment;
import ru.otus.spring.volgin.repository.CommentRepository;

import static java.util.Optional.empty;
import static java.util.Optional.of;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@DisplayName("Тест сервиса для работы с комментариями")
@ExtendWith(MockitoExtension.class)
class CommentServiceImplTest {

    @Mock
    private CommentRepository commentRepository;
    @Mock
    private BookService bookService;
    @InjectMocks
    private CommentServiceImpl commentService;

    @DisplayName("Тест сохранения комментария")
    @Test
    void saveTest() {
        var expectedComment = getCommentForSave();
        var comment = expectedComment.getBook();
        when(bookService.findById(comment.getId())).thenReturn(comment);
        commentService.save(expectedComment.getText(), comment.getId());
        verify(commentRepository).save(argThat(actualSavedBookComment -> {
            assertThat(actualSavedBookComment)
                    .usingRecursiveComparison()
                    .isEqualTo(expectedComment);
            return true;
        }));
    }

    @DisplayName("Тест обновления комментария")
    @Test
    void updateTest() {
        var expectedComment = getCommentForSave();
        expectedComment.setId(3L);
        when(commentRepository.findById(expectedComment.getId()))
                .thenReturn(of(Comment.builder().id(expectedComment.getId()).book(expectedComment.getBook()).build()));
        commentService.update(expectedComment.getId(), expectedComment.getText());
        verify(commentRepository).save(argThat(actualSavedComment -> {
            assertThat(actualSavedComment)
                    .usingRecursiveComparison()
                    .isEqualTo(expectedComment);
            return true;
        }));
    }

    @DisplayName("Тест поиска по идентификатору комментария")
    @Test
    void findById() {
        when(commentRepository.findById(1L)).thenReturn(of(Comment.builder().build()));
        when(commentRepository.findById(2L)).thenReturn(empty());
        assertThatCode(() -> commentService.findById(1))
                .doesNotThrowAnyException();
        assertThatThrownBy(() -> commentService.findById(2))
                .isInstanceOf(IllegalArgumentException.class);
    }

    private Comment getCommentForSave() {
        return Comment.builder()
                .text("Тестовый комментария")
                .book(Book.builder().id(1L).title("Какая-то книга").build())
                .build();
    }
}
