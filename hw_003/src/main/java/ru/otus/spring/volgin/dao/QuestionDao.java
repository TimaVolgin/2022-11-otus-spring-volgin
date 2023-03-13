package ru.otus.spring.volgin.dao;

import ru.otus.spring.volgin.domain.Question;

import java.util.List;

/**
 * Контракт для работы с вопросами тестирования
 */
public interface QuestionDao {

    /**
     * Возвращает все вопросы тестирования
     * @return вопросы тестирования
     */
    List<Question> findAll();
}
