package ru.otus.spring.volgin.service;

import lombok.RequiredArgsConstructor;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.volgin.domain.Book;
import ru.otus.spring.volgin.repository.BookRepository;

import java.time.LocalDate;
import java.util.List;

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
    @Transactional
    public Book save(String title, String published, long genreId, long authorId) {
        var genre = genreService.findById(genreId);
        var author = authorService.findById(authorId);
        var book = Book.builder()
                .title(title)
                .published(LocalDate.parse(published))
                .genre(genre)
                .author(author)
                .build();
        return bookRepository.saveOrUpdate(book);
    }

    @Override
    @Transactional
    public Book update(long id, String title, String published, long genreId, long authorId) {
        var genre = genreService.findById(genreId);
        var author = authorService.findById(authorId);
        var book = findById(id);
        book.setTitle(title);
        book.setPublished(LocalDate.parse(published));
        book.setGenre(genre);
        book.setAuthor(author);
        return bookRepository.saveOrUpdate(book);
    }

    @Override
    @Transactional
    public void deleteById(long id) {
        bookRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Book findById(long id) {
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
        var genre= genreService.findByName(genreName);
        Hibernate.initialize(genre.getBooks());
        return genre.getBooks();
    }
}
