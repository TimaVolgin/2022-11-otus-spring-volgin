package ru.otus.spring.volgin.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.volgin.domain.Book;
import ru.otus.spring.volgin.domain.Genre;
import ru.otus.spring.volgin.repository.BookRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import static java.text.MessageFormat.format;

/**
 * Сервис для работы с книгами
 */
@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    /** Репозиторий для работы с книгами */
    private final BookRepository bookRepository;
    /** Репозиторий для работы с жанрами */
    private final GenreService genreService;
    /** Репозиторий для работы с авторами */
    private final AuthorService authorService;

    @Override
    public Book save(String title, String published, String genreId, String authorId) {
        var genre = genreService.findById(genreId);
        var author = authorService.findById(authorId);
        var book = Book.builder()
                .title(title)
                .published(LocalDate.parse(published))
                .genre(genre)
                .author(author)
                .build();
        return bookRepository.save(book);
    }

    @Override
    public Book update(String id, String title, String published, String genreId, String authorId) {
        var genre = genreService.findById(genreId);
        var author = authorService.findById(authorId);
        var book = findById(id);
        book.setTitle(title);
        book.setPublished(LocalDate.parse(published));
        book.setGenre(genre);
        book.setAuthor(author);
        return bookRepository.save(book);
    }

    @Override
    public void deleteById(String id) {
        bookRepository.deleteById(id);
    }

    @Override
    public Book findById(String id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(format("Не найдена книга с идентификатором {0}", id)));
    }

    @Override
    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Book> findAllByGenreName(String genreName) {
        List<Genre> genre = genreService.findByNameContainingText(genreName);
        return genre.stream()
                .map(Genre::getId)
                .map(bookRepository::findAllByGenreId)
                .flatMap(List::stream)
                .collect(Collectors.toList());
    }
}
