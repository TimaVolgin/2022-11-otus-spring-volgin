package ru.otus.spring.volgin.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.otus.spring.volgin.domain.Author;
import ru.otus.spring.volgin.service.AuthorService;

import java.util.List;
import java.util.stream.Collectors;

import static java.text.MessageFormat.format;

/**
 * Shell команды для работы с автором
 */
@ShellComponent
@RequiredArgsConstructor
public class AuthorCommands {

    /** Сервис для работы с авторами */
    private final AuthorService authorService;

    @ShellMethod(key = {"ia", "insertAuthor"},
            value = "Добавляет автора. Пример: insertAuthor 'Иванов И.И.' 1777-07-07")
    public String insert(@ShellOption(value = {"fio"}, help = "ФИО автора") String fio,
                         @ShellOption(value = {"birthDay"}, help = "Дата рождения автора") String birthDay) {
        return format("Автор сохранён: {0}", authorService.save(fio, birthDay));
    }

    @ShellMethod(key = {"la", "listAuthors"}, value = "Выводит список авторов")
    public String showAll() {
        return getAuthorsAsString(authorService.findAll());
    }

    private String getAuthorsAsString(List<Author> authors) {
        return format("Список авторов:\n{0}", authors.stream()
                .map(Author::toString)
                .collect(Collectors.joining("\n"))
        );
    }
}
