package ru.otus.spring.volgin.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.otus.spring.volgin.domain.Author;
import ru.otus.spring.volgin.domain.Book;
import ru.otus.spring.volgin.domain.Genre;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("Тест репозитория для работы с книгами")
@DataJpaTest
@Import({BookRepositoryJpa.class})
class BookRepositoryJpaTest {

    @Autowired
    private BookRepositoryJpa bookRepositoryJpa;
    @Autowired
    private TestEntityManager em;

    @DisplayName("Тест сохранения книги")
    @Test
    void saveTest() {
        Book expectedBook = Book.builder()
                .title("Дон Жуан")
                .published(LocalDate.now())
                .genre(Genre.builder()
                        .id(5L)
                        .name("Трагедия")
                        .build())
                .author(Author.builder()
                        .id(3L)
                        .fio("Толстой Алексей Константинович")
                        .birthday(LocalDate.parse("1817-09-05"))
                        .build())
                .build();
        bookRepositoryJpa.saveOrUpdate(expectedBook);
        Optional<Book> actualBook = bookRepositoryJpa.findById(expectedBook.getId());
        assertTrue(actualBook.isPresent(), "Книга не найдена");
        assertThat(actualBook.get()).usingRecursiveComparison().isEqualTo(expectedBook);
    }

    @DisplayName("Тест обновления книги")
    @Test
    void updateTest() {
        Book expectedBook = Book.builder()
                .id(7L)
                .title("Дон Жуан")
                .published(LocalDate.now())
                .genre(Genre.builder()
                        .id(5L)
                        .name("Трагедия")
                        .build())
                .author(Author.builder()
                        .id(3L)
                        .fio("Толстой Алексей Константинович")
                        .birthday(LocalDate.parse("1817-09-05"))
                        .build())
                .build();
        bookRepositoryJpa.saveOrUpdate(expectedBook);
        Optional<Book> actualBook = bookRepositoryJpa.findById(expectedBook.getId());
        assertTrue(actualBook.isPresent(), "Не найдена книга");
        assertThat(actualBook.get()).usingRecursiveComparison().isEqualTo(expectedBook);
    }

    @DisplayName("Тест удаления книги")
    @Test
    void deleteByIdTest() {
        var existingBookId = getExistingBook().getId();
        assertThat(bookRepositoryJpa.findById(existingBookId)).isNotEmpty();
        bookRepositoryJpa.deleteById(existingBookId);
        assertThat(bookRepositoryJpa.findById(existingBookId)).isEmpty();
    }

    @DisplayName("Тест поиска книги по идентификатору")
    @Test
    void findByIdTest() {
        Book expectedBook = getExistingBook();
        Optional<Book> actualBook = bookRepositoryJpa.findById(expectedBook.getId());
        assertTrue(actualBook.isPresent(), "Не найден автор");
        assertThat(actualBook.get()).usingRecursiveComparison().isEqualTo(expectedBook);
    }

    @DisplayName("Тест поиска всех записей")
    @Test
    void findAllTest() {
        Book expectedBook = getExistingBook();
        List<Book> actualBookList = bookRepositoryJpa.findAll();
        assertThat(actualBookList)
                .usingRecursiveFieldByFieldElementComparator()
                .contains(expectedBook);
        assertEquals(7, actualBookList.size(), "Неверное количество записей");
    }

    private Book getExistingBook() {
        var book = bookRepositoryJpa.findById(6L).orElseThrow(() -> new IllegalStateException("Ожидаемая книга не существует"));
        em.detach(book);
        return book;
    }
}
