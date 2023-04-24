package ru.otus.spring.volgin.mongock.changelog;

import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import com.github.cloudyrock.mongock.driver.mongodb.springdata.v3.decorator.impl.MongockTemplate;
import com.mongodb.client.MongoDatabase;
import ru.otus.spring.volgin.domain.Author;
import ru.otus.spring.volgin.domain.Book;
import ru.otus.spring.volgin.domain.Comment;
import ru.otus.spring.volgin.domain.Genre;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Set;

@ChangeLog(order = "001")
public class DatabaseChangelog {

    @ChangeSet(order = "001", id = "dropDatabase", author = "VolginTA", runAlways = true)
    public void dropDatabase(MongoDatabase database) {
        database.drop();
    }

    @ChangeSet(order = "002", id = "insertAuthors", author = "VolginTA")
    public void insertAuthors(MongockTemplate template) {
        ArrayList<Author> authors = new ArrayList<>();
        authors.add(Author.builder()
                .id("1")
                .fio("Александр Сергеевич Пушкин")
                .birthday(LocalDate.parse("1799-06-06"))
                .build());
        authors.add(Author.builder()
                .id("2")
                .fio("Михаил Юрьевич Лермонтов")
                .birthday(LocalDate.parse("1814-10-15"))
                .build());
        template.insertAll(authors);
    }

    @ChangeSet(order = "003", id = "insertGenres", author = "VolginTA")
    public void insertGenres(MongockTemplate template) {
        ArrayList<Genre> genres = new ArrayList<>();
        genres.add(Genre.builder()
                .id("1")
                .name("Поэма")
                .build());
        genres.add(Genre.builder()
                .id("2")
                .name("Повесть")
                .build());
        genres.add(Genre.builder()
                .id("3")
                .name("Роман")
                .build());
        genres.add(Genre.builder()
                .id("4")
                .name("Баллада")
                .build());
        template.insertAll(genres);
    }

    @ChangeSet(order = "004", id = "insertBooks", author = "VolginTA")
    public void insertBooks(MongockTemplate template) {
        ArrayList<Book> books = new ArrayList<>();
        books.add(Book.builder()
                .id("1")
                .title("Руслан и Людмила")
                .published(LocalDate.parse("1820-04-01"))
                .genre(Genre.builder().id("1").build())
                .author(Author.builder().id("1").build())
                .build());
        books.add(Book.builder()
                .id("2")
                .title("Пиковая дама")
                .published(LocalDate.parse("1834-04-01"))
                .genre(Genre.builder().id("2").build())
                .author(Author.builder().id("1").build())
                .build());
        books.add(Book.builder()
                .id("3")
                .title("Евгений Онегин")
                .published(LocalDate.parse("1831-10-05"))
                .genre(Genre.builder().id("3").build())
                .author(Author.builder().id("1").build())
                .build());
        books.add(Book.builder()
                .id("4")
                .title("Бородино")
                .published(LocalDate.parse("1812-09-07"))
                .genre(Genre.builder().id("4").build())
                .author(Author.builder().id("2").build())
                .build());
        books.add(Book.builder()
                .id("5")
                .title("Герой нашего времени")
                .published(LocalDate.parse("1839-03-01"))
                .genre(Genre.builder().id("3").build())
                .author(Author.builder().id("2").build())
                .build());
        template.insertAll(books);
    }

    @ChangeSet(order = "005", id = "insertComments", author = "VolginTA")
    public void insertComments(MongockTemplate template) {
        Book book = template.findById("1", Book.class);
        Objects.requireNonNull(book).setComments(Set.of(
                new Comment("Всем советую прочесть"),
                new Comment("Замечательная книга"),
                new Comment("Не забываемые впечатления от прочтения")
        ));
        template.save(book);

        book = template.findById("2", Book.class);
        Objects.requireNonNull(book).setComments(Set.of(
                new Comment("Серьезное произведение"),
                new Comment("Есть над чем подумать")
        ));
        template.save(book);

        book = template.findById("3", Book.class);
        Objects.requireNonNull(book).setComments(Set.of(
                new Comment("Лучшее из прочитанного")
        ));
        template.save(book);

        book = template.findById("4", Book.class);
        Objects.requireNonNull(book).setComments(Set.of(
                new Comment("Советую прочитать каждому")
        ));
        template.save(book);

        book = template.findById("5", Book.class);
        Objects.requireNonNull(book).setComments(Set.of(
                new Comment("Очень современно и на злобу дня")
        ));
        template.save(book);
    }
}
