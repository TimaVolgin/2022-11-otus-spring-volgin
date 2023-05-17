package ru.otus.spring.volgin.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ru.otus.spring.volgin.dto.GenreDto;
import ru.otus.spring.volgin.service.GenreService;

import javax.validation.Valid;

/**
 * Контроллер работы с жанрами
 */
@Controller
@RequiredArgsConstructor
public class GenreController {

    /** Форма по заведению книги */
    private static final String GENRE_FORM = "genre/form";
    /** Сервис работы с жанрами */
    private final GenreService genreService;

    /**
     * Отображает форму заведения новой книги
     * @param model модель данных
     * @return форма заведения новой книги
     */
    @GetMapping("/genre/new")
    public String newGenre(Model model) {
        model.addAttribute("genre", new GenreDto());
        return GENRE_FORM;
    }

    /**
     * Обновление жанра
     * @param id    идентификатор жанра
     * @param model модель данных
     * @return форма обновления жанра
     */
    @GetMapping("genre/{id}/update")
    public String updateGenre(@PathVariable Long id, Model model) {
        model.addAttribute("genre", genreService.findById(id));
        return GENRE_FORM;
    }

    /**
     * Процесс сохранения жанра
     * @param genre жанр
     * @return форма отображаемая после обработки
     */
    @PostMapping("genre")
    public String saveOrUpdate(@Valid @ModelAttribute("genre") GenreDto genre) {
        genreService.save(genre.getName());
        return "redirect:/";
    }
}
