package ru.otus.spring.volgin.service;

import ru.otus.spring.volgin.domain.Answer;
import ru.otus.spring.volgin.domain.Exam;
import ru.otus.spring.volgin.domain.Question;

/**
 * Контракт правил экзамена
 */
public interface ExamRulesService {

    /**
     * Возвращает количество правильных ответов студента
     * @param exam экзамен
     * @return количество правильных ответов студента
     */
    long getCorrectAnswerCount(Exam exam);

    /**
     * Возвращает результат выполнения экзамена
     * @param exam экзамен
     * @return результат выполнения экзамена
     */
    boolean isExamPassed(Exam exam);

    /**
     * Возвращает признак верности ответа
     * @return признак верности ответа
     */
    boolean isRightAnswer(Answer answer);

    /**
     * Перемешивает ответы на вопросы
     */
    void shuffleAnswers(Question question);
}
