package ru.otus.spring.volgin.dao;

import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import ru.otus.spring.volgin.domain.Question;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Тест чтения csv файла с вопросами")
class QuestionDaoCsvTest {

    @Test
    @DisplayName("Тест чтения вопросов с корректного файла")
    void testCorrectData() {
        QuestionDaoCsv questionDaoCsv = new QuestionDaoCsv("questionsCorrectData.csv");
        List<Question> questionList = questionDaoCsv.findAll();
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
        QuestionDaoCsv questionDaoCsv = new QuestionDaoCsv(resourceName);
        Exception e = assertThrows(RuntimeException.class, questionDaoCsv::findAll, "Ожидается ошибка о том, что не заполнено обязательное поле");
        assertEquals(CsvRequiredFieldEmptyException.class, e.getCause().getClass(), "Ожидается другая ошибка");
    }
}
