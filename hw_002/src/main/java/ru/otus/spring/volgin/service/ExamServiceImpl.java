package ru.otus.spring.volgin.service;

import org.springframework.beans.factory.annotation.Value;
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
     * Экзамен
     */
    private final Exam exam;
    /**
     * Сервис по взаимодействию со студентом
     */
    private final InteractionService interactionService;

    public ExamServiceImpl(QuestionDao questionDao,
                           QuestionValidator questionValidator,
                           InteractionService interactionService,
                           @Value("${exam.requiredCorrectAnswers}") int requiredCorrectAnswers,
                           @Value("${exam.questionAttempts}") int questionAttempts,
                           @Value("${exam.shuffleAnswers}") boolean shuffleAnswers) {
        this.questionDao = questionDao;
        this.questionValidator = questionValidator;
        this.exam = Exam.builder()
                .requiredCorrectAnswers(requiredCorrectAnswers)
                .questionAttempts(questionAttempts)
                .shuffleAnswers(shuffleAnswers)
                .build();
        this.interactionService = interactionService;
    }

    @Override
    public void execute() {
        List<Question> questions = questionDao.findAll();
        questionValidator.validate(questions);
        exam.setQuestions(questions);
        exam.setStudent(interactionService.getStudentInfo());
        exam.getQuestions().stream()
                .map(question -> {
                    interactionService.askQuestion(question, exam.isShuffleAnswers());
                    return interactionService.getAnswer(question, exam.getQuestionAttempts());
                })
                .forEach(exam::addStudentAnswer);
        interactionService.printResult(exam);
    }
}
