package ru.otus.spring.volgin.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.otus.spring.volgin.domain.Comment;
import ru.otus.spring.volgin.service.CommentService;

import java.util.stream.Collectors;

import static java.text.MessageFormat.format;

/**
 * Shell команды для работы с жанрами
 */
@ShellComponent
@RequiredArgsConstructor
public class CommentCommands {

    /** Сервис для работы с комментариями */
    private final CommentService commentService;

    @ShellMethod(key = {"ic", "insertComment"},
            value = "Добавляет комментарий для книги. Пример: insertComment 'Читаем всей семьей' 2")
    public String insert(@ShellOption(value = {"text"}, help = "Текст комментария") String text,
                         @ShellOption(value = {"bookId"}, help = "Идентификатор книги") long bookId) {
        return format("Комментарий сохранён: {0}", commentService.save(text, bookId));
    }

    @ShellMethod(key = {"uc","updateComment"},
            value = "Изменяет комментарий. Пример: updateComment 2 'Удивительная книга'")
    public String update(@ShellOption(value = {"id"}, help = "Идентификатор модифицируемого комментария") long id,
                         @ShellOption(value = {"text"}, help = "Текст комментария") String text) {
        return format("Комментарий изменен: {0}", commentService.update(id, text));
    }

    @ShellMethod(key = {"lc", "listComment"}, value = "Выводит список комментариев")
    public String showAll() {
        var bookComments = commentService.findAll();
        return format("Список комментариев:\n\t{0}", bookComments.stream()
                .map(Comment::toString)
                .collect(Collectors.joining("\n\t")));
    }

    @ShellMethod(key = {"fc", "findComment"},
            value = "Ищет комментарий по идентификатору. Пример: findComment 1")
    public String show(@ShellOption(value = {"id"}, help = "Идентификатор искомого комментария") long id) {
        return format("Комментарий найден: {0}", commentService.findById(id));
    }

    @ShellMethod(key = {"fcbi", "findCommentsByBookId"},
            value = "Ищет комментарий по идентификатору книги. Пример: findCommentsByBookId 1")
    public String findCommentsByBookId(@ShellOption(value = {"id"}, help = "Идентификатор книги") long id) {
        return format("Список комментариев:\n\t{0}",
                commentService.findBookCommentsByBookId(id).stream()
                        .map(Comment::toString)
                        .collect(Collectors.joining("\n\t")));
    }

    @ShellMethod(key = {"dc", "deleteComment"},
            value = "Удаляет комментарий. Пример: deleteComment 1")
    public String delete(@ShellOption(value = {"id"}, help = "Идентификатор удаляемого комментария") long id) {
        commentService.deleteById(id);
        return format("Комментарий с идентификатором {0} удален", id);
    }
}
