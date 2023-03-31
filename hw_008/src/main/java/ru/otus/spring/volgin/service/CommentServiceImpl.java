package ru.otus.spring.volgin.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.spring.volgin.domain.Book;
import ru.otus.spring.volgin.domain.Comment;
import ru.otus.spring.volgin.repository.BookRepository;
import ru.otus.spring.volgin.repository.CommentRepository;

import java.util.Set;
import java.util.stream.Collectors;

import static java.text.MessageFormat.format;

/**
 * Сервис по работе с комментариями
 */
@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    /** Репозиторий для работы с комментариями */
    private final CommentRepository commentRepository;
    /** Репозиторий для работы с книгами */
    private final BookRepository bookRepository;

    @Override
    public Comment save(String text, String bookId) {
        return commentRepository.saveComment(text, bookId);
    }

    @Override
    public Comment update(String id, String text) {
        return commentRepository.updateComment(id, text);
    }

    @Override
    public void deleteById(String id) {
        commentRepository.deleteCommentById(id);
    }

    @Override
    public Set<Comment> findByBookId(String bookId) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new IllegalArgumentException(format("Не найдена книга с идентификатором {0}", bookId)));
        return book.getComments();
    }

    @Override
    public Set<Comment> findAll() {
        return bookRepository.findAll()
                .stream()
                .flatMap(b -> b.getComments().stream())
                .collect(Collectors.toSet());
    }
}
