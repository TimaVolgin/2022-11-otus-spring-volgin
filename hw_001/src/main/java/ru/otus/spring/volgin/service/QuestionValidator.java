package ru.otus.spring.volgin.service;

import ru.otus.spring.volgin.domain.Question;

import java.util.List;

/**
 * Контракт проверки списка вопросов на соответствие необходимым требованиям
 */
public interface QuestionValidator {

    /**
     * Проверяет список вопросов на соответствие необходимым требованиям
     * @param questionList список вопросов
     */
    void validate(List<Question> questionList);
}
