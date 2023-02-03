package ru.otus.spring.volgin.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * Информация по экзамену
 */
@Setter
@Getter
@Builder
public class Exam {

    /**
     * Количество правильных ответов, требуемых для зачета экзамена
     */
    private final int requiredCorrectAnswers;
    /**
     * Количество попыток ответа на вопрос
     */
    private final int questionAttempts;
    /**
     * Признак необходимости перемешивать вопросы
     */
    private final boolean shuffleAnswers;
    /**
     * Пользователь
     */
    private Student student;
    /**
     * Вопросы
     */
    private List<Question> questions;
    /**
     * Ответы
     */
    private final List<Answer> studentAnswers = new ArrayList<>();

    /**
     * Добавляет ответ студента
     * @param answer ответ студента
     */
    public void addStudentAnswer(Answer answer) {
        studentAnswers.add(answer);
    }

    /**
     * Возвращает количество правильных ответов студента
     * @return количество правильных ответов студена
     */
    public long getCorrectAnswerCount() {
        return studentAnswers.stream()
                .filter(Answer::isRightAnswer)
                .count();
    }

    /**
     * Возвращает результат выполнения экзамена
     * @return результат выполнения экзамена
     */
    public boolean isExamPassed() {
        return requiredCorrectAnswers <= getCorrectAnswerCount();
    }
}
