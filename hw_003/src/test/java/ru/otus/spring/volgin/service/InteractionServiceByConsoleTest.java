package ru.otus.spring.volgin.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.util.ReflectionTestUtils;
import ru.otus.spring.volgin.domain.Answer;
import ru.otus.spring.volgin.domain.Exam;
import ru.otus.spring.volgin.domain.Question;
import ru.otus.spring.volgin.domain.Student;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@DisplayName("Тест взаимодействия со студентом")
@SpringBootTest
class InteractionServiceByConsoleTest {

    @Mock
    private MessageService messageService;
    @Spy
    private IOService ioService;
    @Autowired
    private ExamRulesService examRulesService;
    @InjectMocks
    private InteractionServiceByConsole interactionService;

    @DisplayName("Тест запроса данных студента")
    @Test
    void studentInfoTest() {
        when(ioService.readAnswer()).thenReturn("Ivan", "Ivanov");
        when(ioService.readIntAnswer()).thenReturn(23);
        Student student = interactionService.getStudentInfo();
        assertEquals("Ivan", student.getFirstName());
        assertEquals("Ivanov", student.getLastName());
        assertEquals(23, student.getAge());
    }

    @DisplayName("Тест перемешивания ответов")
    @Test
    void shuffleTest() {
        ReflectionTestUtils.setField(interactionService, "examRulesService", examRulesService);
        boolean isShuffle = false;
        int startIndex = 0;
        Question question = Question.builder()
                .text("Q1").answers(Arrays.asList("one", "two", "three")).correctAnswerIndex(startIndex).build();
        for (int i = 0; i < 1000; i++) {
            interactionService.askQuestion(question, true);
            assertEquals("one", question.getAnswers().get(question.getCorrectAnswerIndex()), "Неверный ответ");
            if (question.getCorrectAnswerIndex() != startIndex) {
                isShuffle = true;
            }
        }
        assertTrue(isShuffle, "Ответы не перемешивались");
    }

    @DisplayName("Тест на ввод некорректного ответа")
    @Test
    void parseIntTest() {
        when(ioService.readIntAnswer()).thenReturn(-1, 3, 4, 1);
        Question question = Question.builder()
                .text("Q1").answers(Arrays.asList("one", "two", "three")).correctAnswerIndex(1).build();
        Answer answer = interactionService.getAnswer(question, 1);
        verify(ioService, times(4)).readIntAnswer();
        assertEquals(1, answer.getAnswer(), "Неверный ответ");
    }

    @DisplayName("Тест вывода результата")
    @ParameterizedTest(name = "Попыток ответа: {0}")
    @CsvSource({
            "1, false",
            "2, false",
            "3, true"
    })
    void printResultTest(int correctAnswer, boolean isPassed) {
        ReflectionTestUtils.setField(interactionService, "examRulesService", examRulesService);
        when(messageService.getMessage(anyString(), any())).thenReturn(" ");
        Exam exam = Exam.builder().requiredCorrectAnswers(3).questionAttempts(1).shuffleAnswers(false).build();
        exam.setStudent(Student.builder().firstName("Petr").lastName("Petrov").age(35).build());
        exam.setQuestions(new ArrayList<>());
        IntStream.range(0, 5).forEach(index -> {
            Question question = Question.builder()
                    .text("Q1").answers(Collections.emptyList()).correctAnswerIndex(3).build();
            exam.getQuestions().add(question);
            exam.addStudentAnswer(Answer.builder()
                    .question(question)
                    .answer(index < correctAnswer ? 3 : 2)
                    .build());
        });
        interactionService.printResult(exam);
        verify(ioService, times(isPassed ? 0 : 1)).printError(anyString());
        verify(ioService, times(isPassed ? 3 : 2)).print(anyString());
        assertEquals(correctAnswer, examRulesService.getCorrectAnswerCount(exam), "Неверное число правильных ответов");
    }
}
