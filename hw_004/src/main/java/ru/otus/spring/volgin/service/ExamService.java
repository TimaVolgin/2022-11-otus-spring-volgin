package ru.otus.spring.volgin.service;

import ru.otus.spring.volgin.domain.Exam;

/**
 * Контракт тестирования
 */
public interface ExamService {

    /**
     * Выполняет процесс экзамена
     * @param exam экзамен
     */
    void execute(Exam exam);
}
