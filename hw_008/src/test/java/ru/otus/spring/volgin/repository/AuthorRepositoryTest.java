package ru.otus.spring.volgin.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import ru.otus.spring.volgin.domain.Author;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("Тест репозитория для работы с авторами")
@DataMongoTest
class AuthorRepositoryTest {

    @Autowired
    private AuthorRepository authorRepository;

    @DisplayName("Тест сохранения записи")
    @Test
    void saveTest() {
        Author expectedAuthor = Author.builder().fio("Иванов Иван Иванович").birthday(LocalDate.now()).build();
        authorRepository.save(expectedAuthor);
        Optional<Author> actualAuthor = authorRepository.findById(expectedAuthor.getId());
        assertTrue(actualAuthor.isPresent(), "Автор не найден");
        assertThat(actualAuthor.get()).usingRecursiveComparison().isEqualTo(expectedAuthor);
    }

    @DisplayName("Тест поиска списка записей")
    @Test
    void findAllTest() {
        Author expectedAuthor = Author.builder()
                .id("2")
                .fio("Михаил Юрьевич Лермонтов")
                .birthday(LocalDate.parse("1814-10-15"))
                .build();
        List<Author> actualAuthorList = authorRepository.findAll();
        assertThat(actualAuthorList)
                .usingRecursiveFieldByFieldElementComparator()
                .contains(expectedAuthor);
        assertEquals(3, actualAuthorList.size(), "Неверное количество записей");
    }

    @DisplayName("Тест поиска записи по id")
    @Test
    void findByIdTest() {
        Author expectedAuthor = Author.builder()
                .id("2")
                .fio("Михаил Юрьевич Лермонтов")
                .birthday(LocalDate.parse("1814-10-15"))
                .build();
        Optional<Author> actualAuthor = authorRepository.findById(expectedAuthor.getId());
        assertTrue(actualAuthor.isPresent(), "Не найден автор");
        assertThat(actualAuthor.get()).usingRecursiveComparison().isEqualTo(expectedAuthor);
    }
}
