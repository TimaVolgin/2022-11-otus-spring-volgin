package ru.otus.spring.volgin.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.spring.volgin.domain.Book;
import ru.otus.spring.volgin.domain.Comment;
import ru.otus.spring.volgin.dto.CommentDto;
import ru.otus.spring.volgin.exceptions.NotFoundException;
import ru.otus.spring.volgin.mapper.CommentMapper;
import ru.otus.spring.volgin.repository.BookRepository;
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
    private final BookRepository bookRepository;
    /** Преобразователь сущностей в DTO */
    private final CommentMapper commentMapper;

    @HystrixCommand(ignoreExceptions = { NotFoundException.class })
    @Override
    public CommentDto save(Comment comment, long bookId) {
        Book book = getBook(bookId);
        comment.setBook(book);
        return commentMapper.toDto(commentRepository.save(comment));
    }

    @HystrixCommand(ignoreExceptions = { NotFoundException.class })
    @Override
    public CommentDto update(long id, String text) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(format("Не найден комментарий с идентификатором {0}", id)));
        comment.setText(text);
        return commentMapper.toDto(commentRepository.save(comment));
    }

    @HystrixCommand
    @Override
    public void deleteById(long id) {
        commentRepository.deleteById(id);
    }

    @HystrixCommand(ignoreExceptions = { NotFoundException.class })
    @Override
    public CommentDto findById(long id) {
        return commentMapper.toDto(commentRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(format("Не найден комментарий с идентификатором {0}", id))));
    }

    @HystrixCommand(ignoreExceptions = { NotFoundException.class })
    @Override
    public List<CommentDto> findBookCommentsByBookId(long id) {
        Book book = getBook(id);
        return commentMapper.toDto(book.getComments());
    }

    @HystrixCommand
    @Override
    public List<CommentDto> findAll() {
        return commentMapper.toDto(commentRepository.findAll());
    }

    private Book getBook(long bookId) {
        return bookRepository.findById(bookId)
                .orElseThrow(() -> new NotFoundException(format("Не найдена книга с идентификатором {0}", bookId)));
    }
}
