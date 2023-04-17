package ru.otus.spring.volgin.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.spring.volgin.domain.Comment;
import ru.otus.spring.volgin.service.CommentService;

import javax.validation.Valid;

/**
 * Контроллер по работе с комментариями
 */
@RestController
@RequiredArgsConstructor
public class CommentController {

    /** Сервис по работе с комментариями */
    private final CommentService commentService;

    /**
     * Сохраняет комментарий
     * @param bookId  идентификатор книги
     * @param comment комментарий
     */
    @PostMapping("/book/{bookId}/comment")
    public void save(@PathVariable Long bookId, @Valid @RequestBody Comment comment) {
        commentService.save(comment, bookId);
    }
}
