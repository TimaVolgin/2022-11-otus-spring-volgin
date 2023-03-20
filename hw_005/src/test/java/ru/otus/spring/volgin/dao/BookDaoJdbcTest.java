package ru.otus.spring.volgin.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
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

@DisplayName("Тест DAO работы с книгами")
@JdbcTest
@Import({BookDaoJdbc.class})
class BookDaoJdbcTest {

    @Autowired
    private BookDaoJdbc bookDaoJdbc;

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
        bookDaoJdbc.save(expectedBook);
        Optional<Book> actualBook = bookDaoJdbc.findById(expectedBook.getId());
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
        bookDaoJdbc.update(expectedBook);
        Optional<Book> actualBook = bookDaoJdbc.findById(expectedBook.getId());
        assertTrue(actualBook.isPresent(), "Не найдена книга");
        assertThat(actualBook.get()).usingRecursiveComparison().isEqualTo(expectedBook);
    }

    @DisplayName("Тест удаления книги")
    @Test
    void deleteByIdTest() {
        var existingBookId = getExistingBook().getId();
        assertThat(bookDaoJdbc.findById(existingBookId)).isNotEmpty();
        bookDaoJdbc.deleteById(existingBookId);
        assertThat(bookDaoJdbc.findById(existingBookId)).isEmpty();
    }

    @DisplayName("Тест поиска книги по идентификатору")
    @Test
    void findByIdTest() {
        Book expectedBook = getExistingBook();
        Optional<Book> actualBook = bookDaoJdbc.findById(expectedBook.getId());
        assertTrue(actualBook.isPresent(), "Не найден автор");
        assertThat(actualBook.get()).usingRecursiveComparison().isEqualTo(expectedBook);
    }

    @DisplayName("Тест поиска всех записей")
    @Test
    void findAllTest() {
        Book expectedBook = getExistingBook();
        List<Book> actualBookList = bookDaoJdbc.findAll();
        assertThat(actualBookList)
                .usingRecursiveFieldByFieldElementComparator()
                .contains(expectedBook);
        assertEquals(7, actualBookList.size(), "Неверное количество записей");
    }

    @DisplayName("Тест поиска книг по названию жанра")
    @Test
    void findAllByGenreNameTest() {
        List<Book> actualBookList = bookDaoJdbc.findAllByGenreName("Трагедия");
        assertThat(actualBookList)
                .usingRecursiveFieldByFieldElementComparator()
                .contains(getExistingBook());
        assertEquals(2, actualBookList.size(), "Неверное количество записей");
    }

    private static Book getExistingBook() {
        return Book.builder()
                .id(6L)
                .title("Царь Борис")
                .published(LocalDate.parse("1870-01-01"))
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
    }
}
