package ru.otus.spring.volgin.domain;

import lombok.Builder;
import lombok.Getter;

/**
 * Ответ на вопрос
 */
@Getter
@Builder
public class Answer {
    /**
     * Вопрос
     */
    private final Question question;
    /**
     * Ответ
     */
    private final int answer;

    /**
     * Возвращает признак верности ответа
     * @return признак верности ответа
     */
    public boolean isRightAnswer() {
        return question.getCorrectAnswerIndex() == answer;
    }
}
