package ru.otus.spring.volgin.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.otus.spring.volgin.domain.Genre;
import ru.otus.spring.volgin.service.GenreService;

import java.util.stream.Collectors;

import static java.text.MessageFormat.format;

/**
 * Shell команды для работы с жанром
 */
@ShellComponent
@RequiredArgsConstructor
public class GenreCommands {

    /** Сервис для работы с жанрами */
    private final GenreService genreService;

    @ShellMethod(key = {"ig", "insertGenre"}, value = "Добавляет жанр. Пример: insertGenre Сказка")
    public String insert(@ShellOption(value = {"name"}, help = "Название жанра") String name) {
        var genre = genreService.save(name);
        return format("Жанр сохранён: {0}", genre);
    }

    @ShellMethod(key = {"lg", "listGenre"}, value = "Выводит список жанров")
    public String showAll() {
        var genres = genreService.findAll();
        return format("Список жанров:\n{0}", genres.stream()
                .map(Genre::toString)
                .collect(Collectors.joining("\n")));
    }

    @ShellMethod(key = {"fg", "findGenre"}, value = "Ищет жанр по названию Пример: findGenre Баллада")
    public String findGenre(@ShellOption(value = {"text"}, help = "Текст в названии жанра") String text) {
        var genres = genreService.findByNameContainingText(text);
        return format("Найден список жанров, содержащих искомый текст:\n{0}", genres.stream()
                .map(Genre::toString)
                .collect(Collectors.joining("\n")));
    }
}
