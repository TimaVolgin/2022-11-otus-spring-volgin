package ru.otus.spring.volgin.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.otus.spring.volgin.domain.Book;
import ru.otus.spring.volgin.service.BookService;

import java.util.List;
import java.util.stream.Collectors;

import static java.text.MessageFormat.format;

/**
 * Shell команды для работы с книгой
 */
@ShellComponent
@RequiredArgsConstructor
public class BookCommands {

    /** Сервис для работы с книгами */
    private final BookService bookService;

    @ShellMethod(key = {"ib", "insertBook"},
            value = "Добавляет книгу. Пример: insertBook 'Неизвестная книга' 1111-01-01 1 1")
    public String insert(@ShellOption(value = {"title"}, help = "Название книги") String title,
                         @ShellOption(value = {"published"}, help = "Дата публикации книги") String published,
                         @ShellOption(value = {"genreId"}, help = "Идентификатор жанра") String genreId,
                         @ShellOption(value = {"authorId"}, help = "Идентификатор автора") String authorId) {
        return format("Книга сохранена: {0}", bookService.save(title, published, genreId, authorId));
    }

    @ShellMethod(key = {"ub", "updateBook"},
            value = "Изменяет книгу. Пример: updateBook 1 'Самая известная книга' 1111-01-01 1 1")
    public String update(@ShellOption(value = {"id"}, help = "Идентификатор модифицируемой книги") String id,
                         @ShellOption(value = {"title"}, help = "Название книги") String title,
                         @ShellOption(value = {"published"}, help = "Дата публикации книги") String published,
                         @ShellOption(value = {"genreId"}, help = "Идентификатор жанра") String genreId,
                         @ShellOption(value = {"authorId"}, help = "Идентификатор автора") String authorId) {
        return format("Книга изменена: {0}", bookService.update(id, title, published, genreId, authorId));
    }

    @ShellMethod(key = {"db", "deleteBook"},
            value = "Удаляет книгу. Пример: deleteBook 1")
    public String delete(@ShellOption(value = {"id"}, help = "Идентификатор удаляемой книги") String id) {
        bookService.deleteById(id);
        return format("Книга с идентификатором {0} удалена", id);
    }

    @ShellMethod(key = {"fb", "findBook"},
            value = "Ищет книгу по идентификатору. Пример: findBook 1")
    public String show(@ShellOption(value = {"id"}, help = "Идентификатор искомой книги") String id) {
        return format("Книга найдена: {0}", bookService.findById(id));
    }

    @ShellMethod(key = {"lb", "listBooks"}, value = "Выводит список книг")
    public String showAll() {
        return getBookAsString(bookService.findAll());
    }

    @ShellMethod(key = {"fbg", "findByGenre"},
            value = "Ищет книги по жанру. Пример: findByGenre Повесть")
    public String showAllByGenreName(@ShellOption(value = {"genre"}, help = "Текст, содержащийся в названии жанра") String genreNameText) {
        return getBookAsString(bookService.findAllByGenreName(genreNameText));
    }

    private String getBookAsString(List<Book> books) {
        return format("Список книг:\n{0}", books.stream()
                .map(Book::toString)
                .collect(Collectors.joining("\n"))
        );
    }
}
