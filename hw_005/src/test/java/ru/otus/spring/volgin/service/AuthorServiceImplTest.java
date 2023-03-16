package ru.otus.spring.volgin.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.spring.volgin.dao.AuthorDao;
import ru.otus.spring.volgin.domain.Author;

import java.time.LocalDate;

import static java.util.Optional.empty;
import static java.util.Optional.of;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@DisplayName("Тест сервиса по работе с авторами")
@ExtendWith(MockitoExtension.class)
class AuthorServiceImplTest {

    @Mock
    private AuthorDao authorDao;
    @InjectMocks
    private AuthorServiceImpl authorService;

    @DisplayName("Тест сохранения автора")
    @Test
    void saveTest() {
        var expectedSavedAuthor = Author.builder()
                .fio("Иванов Иван Иванович")
                .birthday(LocalDate.now())
                .build();
        authorService.save(expectedSavedAuthor.getFio(), expectedSavedAuthor.getBirthday().toString());
        verify(authorDao).save(argThat(actualSavedAuthor -> {
            assertThat(actualSavedAuthor)
                    .usingRecursiveComparison()
                    .isEqualTo(expectedSavedAuthor);
            return true;
        }));
    }

    @DisplayName("Тест поиска по идентификатору автора")
    @Test
    void findByIdTest() {
        when(authorDao.findById(1)).thenReturn(of(Author.builder().build()));
        when(authorDao.findById(2)).thenReturn(empty());
        assertThatCode(() -> authorService.findById(1))
                .doesNotThrowAnyException();
        assertThatThrownBy(() -> authorService.findById(2))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
