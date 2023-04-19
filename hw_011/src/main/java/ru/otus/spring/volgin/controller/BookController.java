package ru.otus.spring.volgin.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import ru.otus.spring.volgin.domain.Comment;
import ru.otus.spring.volgin.dto.BookDto;
import ru.otus.spring.volgin.dto.BookWithCommentsDto;
import ru.otus.spring.volgin.exceptions.NotFoundException;
import ru.otus.spring.volgin.mapper.BookMapper;
import ru.otus.spring.volgin.repository.AuthorRepository;
import ru.otus.spring.volgin.repository.BookRepository;
import ru.otus.spring.volgin.repository.GenreRepository;

import javax.validation.Valid;

import static java.text.MessageFormat.format;


/**
 * Контроллер работы с книгами
 */
@RestController
@RequiredArgsConstructor
public class BookController {

    /** Репозиторий работы с книгами */
    private final BookRepository bookRepository;
    /** Репозиторий работы с авторами */
    private final AuthorRepository authorRepository;
    /** Репозиторий работы с жанрами */
    private final GenreRepository genreRepository;
    /** Маппер для работы с книгами */
    private final BookMapper bookMapper;

    /**
     * Возвращает список книг
     * @param pageable пагинация и сортировка
     * @return список книг
     */
    @GetMapping("/books")
    public Mono<PageImpl<BookDto>> getBooks(Pageable pageable) {
        return bookRepository.count()
                .flatMap(booksCount -> this.bookRepository.findAll(pageable)
                        .map(bookMapper::toDto)
                        .collectList()
                        .map(books -> new PageImpl<>(books, pageable, booksCount)));

    }

    /**
     * Возвращает книгу
     * @param bookId идентификатор книги
     * @return книга
     */
    @GetMapping("/book/{bookId}")
    public Mono<BookWithCommentsDto> getBook(@PathVariable String bookId) {
        return bookRepository.findById(bookId)
                .switchIfEmpty(Mono.error(new NotFoundException(format("Не найдена книга с идентификатором {0}", bookId))))
                .map(bookMapper::toDtoWithComments);
    }

    /**
     * Сохраняет или обновляет книгу
     * @param bookDto книга
     * @return книга
     */
    @PostMapping("/book")
    public Mono<BookDto> saveOrUpdate(@RequestBody BookDto bookDto) {
        return Mono.just(bookDto)
                .map(bookMapper::fromDto)
                .flatMap(book -> genreRepository.findById(bookDto.getGenre().getId())
                        .switchIfEmpty(Mono.error(new NotFoundException(format("Не найден жанр с идентификатором {0}", bookDto.getGenre().getId()))))
                        .map(genre -> {
                            book.setGenre(genre);
                            return book;
                        }))
                .flatMap(book -> authorRepository.findById(bookDto.getAuthor().getId())
                        .switchIfEmpty(Mono.error(new NotFoundException(format("Не найден автор с идентификатором {0}", bookDto.getAuthor().getId()))))
                        .map(author -> {
                            book.setAuthor(author);
                            return book;
                        }))
                .flatMap(bookRepository::save)
                .map(bookMapper::toDto);
    }

    /**
     * Сохраняет комментарий
     * @param bookId  идентификатор книги
     * @param comment комментарий
     */
    @PostMapping("/book/{bookId}/comment")
    public Mono<Void> save(@PathVariable String bookId, @Valid @RequestBody Comment comment) {
        return bookRepository.findById(bookId)
                .switchIfEmpty(Mono.error(new NotFoundException(format("Не найдена книга с идентификатором {0}", bookId))))
                .map(book -> {
                    book.getComments().add(comment);
                    return book;
                }).flatMap(bookRepository::save)
                .then();
    }

    /**
     * Удаляет книгу
     * @param bookId идентификатор книги
     */
    @DeleteMapping("/book/{bookId}")
    public Mono<Void> deleteBook(@PathVariable String bookId) {
        return bookRepository.deleteById(bookId);
    }
}
