package ru.otus.spring.volgin.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.spring.volgin.domain.Author;
import ru.otus.spring.volgin.domain.Book;
import ru.otus.spring.volgin.domain.Genre;
import ru.otus.spring.volgin.repository.BookRepository;

import java.time.LocalDate;

import static java.util.Optional.empty;
import static java.util.Optional.of;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@DisplayName("Тест сервиса для работы с книгами")
@ExtendWith(MockitoExtension.class)
class BookServiceImplTest {

    @Mock
    private BookRepository bookRepository;
    @Mock
    private GenreService genreService;
    @Mock
    private AuthorService authorService;
    @InjectMocks
    private BookServiceImpl bookService;

    @DisplayName("Тест сохранения книги")
    @Test
    void saveTest() {
        var expectedBook = getBook();
        var bookGenre = expectedBook.getGenre();
        var bookAuthor = expectedBook.getAuthor();
        when(genreService.findById(bookGenre.getId())).thenReturn(bookGenre);
        when(authorService.findById(bookAuthor.getId())).thenReturn(bookAuthor);
        bookService.save(expectedBook.getTitle(), expectedBook.getPublished().toString(), bookGenre.getId(), bookAuthor.getId());
        verify(bookRepository).saveOrUpdate(argThat(actualSavedBook -> {
            assertThat(actualSavedBook)
                    .usingRecursiveComparison()
                    .isEqualTo(expectedBook);
            return true;
        }));
    }

    @DisplayName("Тест обновления книги")
    @Test
    void updateTest() {
        var expectedBook = getBook();
        expectedBook.setId(2L);
        var bookGenre = expectedBook.getGenre();
        var bookAuthor = expectedBook.getAuthor();
        when(genreService.findById(bookGenre.getId())).thenReturn(bookGenre);
        when(authorService.findById(bookAuthor.getId())).thenReturn(bookAuthor);
        when(bookRepository.findById(expectedBook.getId()))
                .thenReturn(of(Book.builder().id(expectedBook.getId()).build()));
        bookService.update(2, expectedBook.getTitle(), expectedBook.getPublished().toString(), bookGenre.getId(), bookAuthor.getId());
        verify(bookRepository).saveOrUpdate(argThat(actualSavedBook -> {
            assertThat(actualSavedBook)
                    .usingRecursiveComparison()
                    .isEqualTo(expectedBook);
            return true;
        }));
    }

    @DisplayName("Тест поиска по идентификатору книги")
    @Test
    void findById() {
        when(bookRepository.findById(1)).thenReturn(of(Book.builder().build()));
        when(bookRepository.findById(2)).thenReturn(empty());
        assertThatCode(() -> bookService.findById(1))
                .doesNotThrowAnyException();
        assertThatThrownBy(() -> bookService.findById(2))
                .isInstanceOf(IllegalArgumentException.class);
    }

    private Book getBook() {
        var bookGenre = Genre.builder().id(2L).name("Повесть").build();
        var bookAuthor = Author.builder().id(2L).fio("Михаил Юрьевич Лермонтов").birthday(LocalDate.now()).build();
        return Book.builder()
                .title("Неизвестная повесть")
                .published(LocalDate.now())
                .genre(bookGenre)
                .author(bookAuthor)
                .build();
    }
}
