package ru.otus.spring.volgin.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.spring.volgin.dao.QuestionDao;
import ru.otus.spring.volgin.domain.Exam;
import ru.otus.spring.volgin.domain.Question;
import ru.otus.spring.volgin.validator.QuestionValidator;

import java.util.List;

/**
 * Сервис проведения экзамена
 */
@Service
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
    /**
     * Сервис по взаимодействию со студентом
     */
    private final InteractionService interactionService;

    @Override
    public void execute(Exam exam) {
        List<Question> questions = questionDao.findAll();
        questionValidator.validate(questions);
        exam.setQuestions(questions);
        exam.getQuestions().stream()
                .map(question -> {
                    interactionService.askQuestion(question, exam.isShuffleAnswers());
                    return interactionService.getAnswer(question, exam.getQuestionAttempts());
                })
                .forEach(exam::addStudentAnswer);
        interactionService.printResult(exam);
    }
}
