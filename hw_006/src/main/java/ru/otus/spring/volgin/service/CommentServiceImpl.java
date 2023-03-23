package ru.otus.spring.volgin.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
    @Transactional
    public Comment save(String text, long bookId) {
        var book = bookService.findById(bookId);
        var bookComment = Comment.builder()
                .text(text)
                .book(book)
                .build();
        return commentRepository.saveOrUpdate(bookComment);
    }

    @Override
    @Transactional
    public Comment update(long id, String text) {
        var comment = findById(id);
        comment.setText(text);
        return commentRepository.saveOrUpdate(comment);
    }

    @Override
    @Transactional
    public void deleteById(long id) {
        commentRepository.deleteById(id);
    }

    @Override
    public Comment findById(long id) {
        return commentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(format("Не найден комментарий с идентификатором {0}", id)));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Comment> findBookCommentsByBookId(long id) {
        var book = bookService.findById(id);
        return book.getComments();
    }

    @Override
    public List<Comment> findAll() {
        return commentRepository.findAll();
    }
}
