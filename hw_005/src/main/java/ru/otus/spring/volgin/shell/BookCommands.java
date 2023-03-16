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
 * Shell команды по работе с книгой
 */
@ShellComponent
@RequiredArgsConstructor
public class BookCommands {

    /** Сервис работы с книгами */
    private final BookService bookService;

    @ShellMethod(key = {"ib", "insBook", "insertBook"},
            value = "Добавляет книгу. Пример: insertBook 'Неизвестная книга' 1111-01-01 1 1")
    public String insert(@ShellOption(value = {"title"}, help = "Название книги") String title,
                         @ShellOption(value = {"published"}, help = "Дата публикации книги") String published,
                         @ShellOption(value = {"genreId"}, help = "Идентификатор жанра") long genreId,
                         @ShellOption(value = {"authorId"}, help = "Идентификатор автора") long authorId) {
        var book = bookService.save(title, published, genreId, authorId);
        return format("Книга сохранена: {0}", book);
    }

    @ShellMethod(key = {"ub", "updateBook"},
            value = "Изменяет книгу. Пример: updateBook 1 'Неизвестная книга' 1111-01-01 1 1")
    public String update(@ShellOption(value = {"id"}, help = "Идентификатор модифицируемой книги") long id,
                         @ShellOption(value = {"title"}, help = "Название книги") String title,
                         @ShellOption(value = {"published"}, help = "Дата публикации книги") String published,
                         @ShellOption(value = {"genreId"}, help = "Идентификатор жанра") long genreId,
                         @ShellOption(value = {"authorId"}, help = "Идентификатор автора") long authorId) {
        var book = bookService.update(id, title, published, genreId, authorId);
        return format("Книга изменена: {0}", book);
    }

    @ShellMethod(key = {"db", "deleteBook"},
            value = "Удаляет книгу. Пример: deleteBook 1")
    public String delete(@ShellOption(value = {"id"}, help = "Идентификатор удаляемой книги") long id) {
        bookService.deleteById(id);
        return format("Книга с идентификатором {0} удалена", id);
    }

    @ShellMethod(key = {"fb", "findBook"},
            value = "Ищет книгу по идентификатору. Пример: findBook 1")
    public String show(@ShellOption(value = {"id"}, help = "Идентификатор искомой книги") long id) {
        var book = bookService.findById(id);
        return format("Книга найдена: {0}", book);
    }

    @ShellMethod(key = {"lb", "listBooks"}, value = "Выводит список книг")
    public String showAll() {
        var books = bookService.findAll();
        return getBookAsString(books);
    }

    @ShellMethod(key = {"fbg", "findByGenre"},
            value = "Ищет книги по жанру. Пример: findByGenre Повесть")
    public String showAllByGenreName(@ShellOption(value = {"genre"}, help = "Название жанра как в справочнике") String genreName) {
        var books = bookService.findAllByGenreName(genreName);
        return getBookAsString(books);
    }

    private String getBookAsString(List<Book> books) {
        return format("Список книг:\n{0}", books.stream()
                .map(Book::toString)
                .collect(Collectors.joining("\n"))
        );
    }
}
