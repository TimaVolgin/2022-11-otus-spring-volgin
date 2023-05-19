package ru.otus.spring.volgin.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ru.otus.spring.volgin.dto.AuthorDto;
import ru.otus.spring.volgin.service.AuthorService;

import javax.validation.Valid;

/**
 * Контроллер работы с авторами
 */
@Controller
@RequiredArgsConstructor
public class AuthorController {

    /** Форма по заведению автора */
    private static final String AUTHOR_FORM = "author/form";
    /** Сервис работы с авторами */
    private final AuthorService authorService;

    /**
     * Отображает форму заведения новой автора
     * @param model модель данных
     * @return форма заведения нового автора
     */
    @GetMapping("/author/new")
    public String newAuthor(Model model) {
        model.addAttribute("author", new AuthorDto());
        return AUTHOR_FORM;
    }

    /**
     * Обновление автора
     * @param id    идентификатор автора
     * @param model модель данных
     * @return форма обновления автора
     */
    @GetMapping("author/{id}/update")
    public String updateAuthor(@PathVariable Long id, Model model) {
        model.addAttribute("author", authorService.findById(id));
        return AUTHOR_FORM;
    }

    /**
     * Процесс сохранения автора
     * @param author автор
     * @return форма отображаемая после обработки
     */
    @PostMapping("author")
    public String saveOrUpdate(@Valid @ModelAttribute("author") AuthorDto author) {
        authorService.save(author.getFio(), author.getBirthday().toString());
        return "redirect:/";
    }
}
