package ru.otus.spring.volgin.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.otus.spring.volgin.domain.Comment;
import ru.otus.spring.volgin.service.BookService;
import ru.otus.spring.volgin.service.CommentService;

/**
 * Контроллер по работе с комментариями
 */
@Controller
@RequiredArgsConstructor
public class CommentController {

    /** Сервис по работе с комментариями */
    private final CommentService commentService;
    /** Сервис по работе с книгами */
    private final BookService bookService;

    /**
     * Возвращает форму добавления комментария
     * @param bookId идентификатор книги
     * @param model модель данных
     * @return форма добавления комментария
     */
    @GetMapping("book/{bookId}/comment/new")
    public String newComment(@PathVariable Long bookId, Model model) {
        bookService.findById(bookId);
        model.addAttribute("bookId", bookId);
        model.addAttribute("comment", new Comment());
        return "book/comment/form";
    }

    /**
     * Сохраняет комментарий
     * @param bookId идентификатор книги
     * @param comment комментарий
     * @return форма просмотра книги
     */
    @PostMapping("book/{bookId}/comment")
    public String save(@ModelAttribute @PathVariable Long bookId, Comment comment) {
        commentService.save(comment, bookId);
        return "redirect:/book/" + bookId + "/view";
    }
}
