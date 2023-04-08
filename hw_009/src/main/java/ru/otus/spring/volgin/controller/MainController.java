package ru.otus.spring.volgin.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.otus.spring.volgin.service.BookService;

/**
 * Контроллер стартовой страницы
 */
@Controller
@RequiredArgsConstructor
public class MainController {

    /** Сервис по работе с книгами */
    private final BookService bookService;

    /**
     * Отображает страницу со всеми книгами
     * @param model модель данных
     * @return страница со всеми книгами
     */
    @RequestMapping({"", "/", "index"})
    public String getIndexPage(Model model) {
        model.addAttribute("books", bookService.findAll());
        return "index";
    }
}
