package ru.otus.spring.volgin.service;

import ru.otus.spring.volgin.domain.Answer;
import ru.otus.spring.volgin.domain.Exam;
import ru.otus.spring.volgin.domain.Question;
import ru.otus.spring.volgin.domain.Student;

/**
 * Контракт взаимодействия со студентом
 */
public interface InteractionService {

    /**
     * Возвращает информацию о студенте
     * @return информация о студенте
     */
    Student getStudentInfo();

    /**
     * Задаёт вопрос студенту
     * @param question вопрос
     * @param shuffle  {@code true}: ответы надо перемешать
     */
    void askQuestion(Question question, boolean shuffle);

    /**
     * Возвращает ответ пользователя
     * @param question вопрос
     * @param attempts количество попыток ответа на вопрос
     * @return ответ пользователя
     */
    Answer getAnswer(Question question, int attempts);

    /**
     * Выводит результат тестирования
     * @param exam экзамен
     */
    void printResult(Exam exam);
}
