package ru.otus.spring.volgin.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import ru.otus.spring.volgin.domain.Book;
import ru.otus.spring.volgin.domain.Comment;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("Тест репозитория для работы с комментариями")
@DataJpaTest
class CommentRepositoryJpaTest {

    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private TestEntityManager em;

    @DisplayName("Тестирует сохранение комментария")
    @Test
    void saveTest() {
        Comment expectedComment = Comment.builder()
                .text("Тестовый комментарий")
                .book(em.find(Book.class, 2L))
                .build();
        commentRepository.save(expectedComment);
        Optional<Comment> actualBookComment = commentRepository.findById(expectedComment.getId());
        assertTrue(actualBookComment.isPresent(), "Не найден комментарий");
        assertThat(actualBookComment.get()).usingRecursiveComparison().isEqualTo(expectedComment);
    }

    @DisplayName("Тестирует обновление книги")
    @Test
    void updateTest() {
        Comment expectedBookComment = Comment.builder()
                .id(11L)
                .text("Тестовый комментарий")
                .build();
        commentRepository.save(expectedBookComment);
        Optional<Comment> actualBookComment = commentRepository.findById(expectedBookComment.getId());
        assertTrue(actualBookComment.isPresent(), "Не найден комментарий");
        assertThat(actualBookComment.get()).usingRecursiveComparison().isEqualTo(expectedBookComment);
    }

    @DisplayName("Тестирует удаление комментария")
    @Test
    void deleteByIdTest() {
        assertThat(commentRepository.findById(11L)).isNotEmpty();
        commentRepository.deleteById(11L);
        em.flush();
        assertThat(commentRepository.findById(11L)).isEmpty();
    }

    @DisplayName("Тестирует поиск комментария по идентификатору")
    @Test
    void findByIdTest() {
        Comment expectedBookComment = Comment.builder()
                .id(5L)
                .text("Есть над чем подумать")
                .book(em.find(Book.class, 2L))
                .build();
        Optional<Comment> actualBook = commentRepository.findById(expectedBookComment.getId());
        assertTrue(actualBook.isPresent(), "Не найден комментарий");
        assertThat(actualBook.get()).usingRecursiveComparison().isEqualTo(expectedBookComment);
    }


    @DisplayName("Тестирует поиск всех комментариев")
    @Test
    void findAllTest() {
        Comment expectedBookComment = Comment.builder()
                .id(5L)
                .text("Есть над чем подумать")
                .book(em.find(Book.class, 2L))
                .build();
        List<Comment> actualBookList = commentRepository.findAll();
        assertThat(actualBookList)
                .usingRecursiveFieldByFieldElementComparator()
                .contains(expectedBookComment);
        assertEquals(11, actualBookList.size(), "Неверное количество записей");
    }
}
