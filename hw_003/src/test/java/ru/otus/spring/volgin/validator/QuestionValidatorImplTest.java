package ru.otus.spring.volgin.validator;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.spring.volgin.domain.Question;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("Тест сервиса проверка данных вопросов тестирования")
@ExtendWith(MockitoExtension.class)
class QuestionValidatorImplTest {

    @InjectMocks
    private QuestionValidatorImpl questionValidator;

    /**
     * Тестирует успешный вывод вопросов
     */
    @ParameterizedTest
    @DisplayName("Тест проверки вопросов на соответствие нужному формату")
    @MethodSource("generateQuestions")
    void testValidate(Question question) {
        assertThrows(IllegalStateException.class, () -> questionValidator.validate(List.of(question)), "Ожидается ошибка отсутствия заполнения нужных полей");
    }

    /**
     * Генерация тестовых наборов данных
     * @return тестовый набор данных
     */
    private static Stream<Arguments> generateQuestions() {
        return Stream.of(
                Arguments.of(new Question("0. Question", List.of("a", "b"), -1)),
                Arguments.of(new Question("1. Question", new ArrayList<>(), 0)),
                Arguments.of(new Question("2. Question", List.of("a", "b", "c"), 3)),
                Arguments.of(new Question("3. Question", List.of("a, b"), 4)),
                Arguments.of(new Question("", List.of("a, b"), 0))
        );
    }
}
