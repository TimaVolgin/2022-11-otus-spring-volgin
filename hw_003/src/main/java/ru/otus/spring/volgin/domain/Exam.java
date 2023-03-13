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
}
