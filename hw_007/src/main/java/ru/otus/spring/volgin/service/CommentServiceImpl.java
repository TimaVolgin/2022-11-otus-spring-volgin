package ru.otus.spring.volgin.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.spring.volgin.domain.Comment;
import ru.otus.spring.volgin.repository.CommentRepository;

import java.util.List;

import static java.text.MessageFormat.format;

/**
 * Сервис по работе с комментариями
 */
@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    /** Репозиторий для работы с комментариями */
    private final CommentRepository commentRepository;
    /** Сервис для работы с книгами */
    private final BookService bookService;

    @Override
    public Comment save(String text, long bookId) {
        var book = bookService.findById(bookId);
        var bookComment = Comment.builder()
                .text(text)
                .book(book)
                .build();
        return commentRepository.save(bookComment);
    }

    @Override
    public Comment update(long id, String text) {
        var comment = findById(id);
        comment.setText(text);
        return commentRepository.save(comment);
    }

    @Override
    public void deleteById(long id) {
        commentRepository.deleteById(id);
    }

    @Override
    public Comment findById(long id) {
        return commentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(format("Не найден комментарий с идентификатором {0}", id)));
    }

    @Override
    public List<Comment> findBookCommentsByBookId(long id) {
        var book = bookService.findById(id);
        return book.getComments();
    }

    @Override
    public List<Comment> findAll() {
        return commentRepository.findAll();
    }
}
