package ru.otus.spring.volgin.dao;

import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.util.ReflectionTestUtils;
import ru.otus.spring.volgin.domain.Question;
import ru.otus.spring.volgin.locale.LocalHolder;

import java.util.List;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@DisplayName("Тест чтения csv файла с вопросами")
@SpringBootTest
class QuestionDaoCsvTest {

    @MockBean
    private LocalHolder localHolder;
    @Autowired
    private QuestionDaoCsv questionDaoCsv;

    @BeforeEach
    void init() {
        when(localHolder.getLocale()).thenReturn(Locale.ENGLISH);
    }

    @Test
    @DisplayName("Тест чтения вопросов с корректного файла")
    void testCorrectData() {
        List<Question> questionList = questionDaoCsv.findAll();
        assertEquals(5, questionList.size(), "Неверное количество записей");
        Question question = questionList.get(0);
        assertAll("Неверно заполнен первый вопрос",
                () -> assertEquals("0. Who killed Julius Caesar?", question.getText(), "Текст вопроса"),
                () -> assertEquals(4, question.getAnswers().size(), "Количество ответов"),
                () -> assertEquals(1, question.getCorrectAnswerIndex()));
        assertEquals(5, questionList.size(), "Неверное количество записей");
    }

    @DisplayName("Тест на проверку обязательных полей")
    @ParameterizedTest(name = "Файл {0}")
    @ValueSource(strings = {"requiredQuestion.csv", "requiredCorrectAnswer.csv", "requiredListAnswers.csv"})
    void testRequiredFields(String resourceName) {
        ReflectionTestUtils.setField(questionDaoCsv, "scvResourceName", resourceName);
        Exception e = assertThrows(RuntimeException.class, questionDaoCsv::findAll, "Ожидается ошибка о том, что не заполнено обязательное поле");
        assertEquals(CsvRequiredFieldEmptyException.class, e.getCause().getClass(), "Ожидается другая ошибка");
    }
}
