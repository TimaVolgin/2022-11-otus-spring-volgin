package ru.otus.spring.volgin.service;

import lombok.RequiredArgsConstructor;
import ru.otus.spring.volgin.dao.QuestionDao;
import ru.otus.spring.volgin.domain.Question;

import java.util.List;

/**
 * Сервис проведения экзамена
 */
@RequiredArgsConstructor
public class ExamServiceImpl implements ExamService {
    /**
     * Контракт для работы с вопросами тестирования
     */
    private final QuestionDao questionDao;
    /**
     * Контракт проверки списка вопросов на соответствие необходимым требованиям
     */
    private final QuestionValidator questionValidator;

    @Override
    public void printQuestions() {
        List<Question> questions = questionDao.findAll();
        questionValidator.validate(questions);
        questions.forEach(question -> {
            System.out.println("Question " + question.getQuestionText() + "\nPossible answers: ");
            question.getAnswers().forEach(System.out::println);
            System.out.println();
        });
    }
}
