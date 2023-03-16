package ru.otus.spring.volgin.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.spring.volgin.dao.BookDao;
import ru.otus.spring.volgin.domain.Book;

import java.time.LocalDate;
import java.util.List;

import static java.text.MessageFormat.format;

/**
 * Сервис по работе с книгами
 */
@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    /** DAO по работе с книгами */
    private final BookDao bookDao;
    /** DAO по работе с жанрами */
    private final GenreService genreService;
    /** DAO по работе с авторами */
    private final AuthorService authorService;

    @Override
    public Book save(String title, String published, long genreId, long authorId) {
        var book = Book.builder()
                .title(title)
                .published(LocalDate.parse(published))
                .genre(genreService.findById(genreId))
                .author(authorService.findById(authorId))
                .build();
        return bookDao.save(book);
    }

    @Override
    public Book update(long id, String title, String published, long genreId, long authorId) {
        var book = findById(id);
        book.setTitle(title);
        book.setPublished(LocalDate.parse(published));
        book.setGenre(genreService.findById(genreId));
        book.setAuthor(authorService.findById(authorId));
        return bookDao.update(book);
    }

    @Override
    public void deleteById(long id) {
        bookDao.deleteById(id);
    }

    @Override
    public Book findById(long id) {
        return bookDao.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(format("Не найдена книга с идентификатором {0}", id)));
    }

    @Override
    public List<Book> findAll() {
        return bookDao.findAll();
    }

    @Override
    public List<Book> findAllByGenreName(String genreName) {
        return bookDao.findAllByGenreName(genreName);
    }
}
