package ru.otus.spring.volgin.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import ru.otus.spring.volgin.domain.Genre;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("Тест репозитория для работы с авторами")
@DataMongoTest
class GenreRepositoryTest {

    @Autowired
    private GenreRepository genreRepository;

    @DisplayName("Тест сохранения записи")
    @Test
    void saveTest() {
        Genre expectedGenre = Genre.builder().name("Историческая поэма").build();
        genreRepository.save(expectedGenre);
        Optional<Genre> actualGenre = genreRepository.findById(expectedGenre.getId());
        assertTrue(actualGenre.isPresent(), "Не найден автор");
        assertThat(actualGenre.get()).usingRecursiveComparison().isEqualTo(expectedGenre);
    }

    @DisplayName("Тест поиска списка записей")
    @Test
    void findAllTest() {
        Genre expectedGenre = Genre.builder()
                .id("4")
                .name("Баллада")
                .build();
        List<Genre> actualGenreList = genreRepository.findAll();
        assertThat(actualGenreList)
                .usingRecursiveFieldByFieldElementComparator()
                .contains(expectedGenre);
        assertEquals(5, actualGenreList.size(), "Неверное количество записей");
    }

    @DisplayName("Тест поиска записи по фио автора")
    @Test
    void findByNameTest() {
        Genre expectedGenre = Genre.builder()
                .id("3")
                .name("Роман")
                .build();
        List<Genre> actualGenre = genreRepository.findByNameLike("Роман");
        assertTrue(!actualGenre.isEmpty(), "Не найден автор");
        assertThat(actualGenre.get(0)).usingRecursiveComparison().isEqualTo(expectedGenre);
    }

    @DisplayName("Тестирует поиск записи по id")
    @Test
    void findByIdTest() {
        Genre expectedGenre = Genre.builder()
                .id("1")
                .name("Поэма")
                .build();
        Optional<Genre> actualGenre = genreRepository.findById(expectedGenre.getId());
        assertTrue(actualGenre.isPresent(), "Не найден автор");
        assertThat(actualGenre.get()).usingRecursiveComparison().isEqualTo(expectedGenre);
    }
}
