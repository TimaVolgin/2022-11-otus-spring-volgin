package ru.otus.spring.volgin.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import ru.otus.spring.volgin.domain.Author;
import ru.otus.spring.volgin.domain.Book;
import ru.otus.spring.volgin.domain.Comment;
import ru.otus.spring.volgin.domain.Genre;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("Тест репозитория для работы с книгами")
@DataMongoTest
class BookRepositoryTest {

    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private MongoTemplate template;

    @DisplayName("Тест сохранения книги")
    @Test
    void saveTest() {
        Book expectedBook = Book.builder()
                .title("Узник")
                .published(LocalDate.now())
                .genre(template.findById("3", Genre.class))
                .author(template.findById("2", Author.class))
                .comments(Set.of(new Comment("Восхитительно!")))
                .build();
        bookRepository.save(expectedBook);
        Optional<Book> actualBook = bookRepository.findById(expectedBook.getId());
        assertTrue(actualBook.isPresent(), "Книга не найдена");
        assertThat(actualBook.get()).usingRecursiveComparison().isEqualTo(expectedBook);
    }

    @DisplayName("Тест обновления книги")
    @Test
    void updateTest() {
        Book expectedBook = Book.builder()
                .id("2")
                .title("Узник")
                .published(LocalDate.now())
                .genre(template.findById("3", Genre.class))
                .author(template.findById("2", Author.class))
                .comments(Set.of(new Comment("Восхитительно!")))
                .build();
        bookRepository.save(expectedBook);
        Optional<Book> actualBook = bookRepository.findById(expectedBook.getId());
        assertTrue(actualBook.isPresent(), "Не найдена книга");
        assertThat(actualBook.get()).usingRecursiveComparison().isEqualTo(expectedBook);
    }

    @DisplayName("Тест удаления книги")
    @Test
    void deleteByIdTest() {
        String existingBookId = "2";
        assertThat(bookRepository.findById(existingBookId)).isNotEmpty();
        bookRepository.deleteById(existingBookId);
        assertThat(bookRepository.findById(existingBookId)).isEmpty();
    }

    @DisplayName("Тест поиска книги по идентификатору")
    @Test
    void findByIdTest() {
        Book expectedBook = getExistingBook();
        Optional<Book> actualBook = bookRepository.findById(expectedBook.getId());
        assertTrue(actualBook.isPresent(), "Не найдена книга");
        assertThat(actualBook.get()).usingRecursiveComparison().isEqualTo(expectedBook);
    }

    @DisplayName("Тест поиска всех записей")
    @Test
    void findAllTest() {
        Book expectedBook = getExistingBook();
        List<Book> actualBookList = bookRepository.findAll();
        assertThat(actualBookList)
                .usingRecursiveFieldByFieldElementComparator()
                .contains(expectedBook);
    }

    private Book getExistingBook() {
        return bookRepository.findById("3").orElseThrow(() -> new IllegalStateException("Ожидаемая книга не существует"));
    }
}
