package ru.otus.spring.volgin.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
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
class BookRepositoryJpaTest {

    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private TestEntityManager em;

    @DisplayName("Тест сохранения книги")
    @Test
    void saveTest() {
        Book expectedBook = Book.builder()
                .title("Дон Жуан")
                .published(LocalDate.now())
                .genre(em.find(Genre.class, 5L))
                .author(em.find(Author.class, 3L))
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
                .id(7L)
                .title("Дон Жуан")
                .published(LocalDate.now())
                .genre(em.find(Genre.class, 5L))
                .author(em.find(Author.class, 3L))
                .build();
        bookRepository.save(expectedBook);
        Optional<Book> actualBook = bookRepository.findById(expectedBook.getId());
        assertTrue(actualBook.isPresent(), "Не найдена книга");
        assertThat(actualBook.get()).usingRecursiveComparison().isEqualTo(expectedBook);
    }

    @DisplayName("Тест удаления книги")
    @Test
    void deleteByIdTest() {
        var existingBookId = getExistingBook().getId();
        assertThat(bookRepository.findById(existingBookId)).isNotEmpty();
        bookRepository.deleteById(existingBookId);
        assertThat(bookRepository.findById(existingBookId)).isEmpty();
    }

    @DisplayName("Тест поиска книги по идентификатору")
    @Test
    void findByIdTest() {
        Book expectedBook = getExistingBook();
        Optional<Book> actualBook = bookRepository.findById(expectedBook.getId());
        assertTrue(actualBook.isPresent(), "Не найден автор");
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
        assertEquals(7, actualBookList.size(), "Неверное количество записей");
    }

    private Book getExistingBook() {
        var book = bookRepository.findById(6L).orElseThrow(() -> new IllegalStateException("Ожидаемая книга не существует"));
        em.detach(book);
        return book;
    }
}
