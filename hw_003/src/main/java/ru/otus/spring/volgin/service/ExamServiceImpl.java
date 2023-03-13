package ru.otus.spring.volgin.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.spring.volgin.config.ExamApplicationProperties;
import ru.otus.spring.volgin.dao.QuestionDao;
import ru.otus.spring.volgin.domain.Exam;
import ru.otus.spring.volgin.domain.Question;
import ru.otus.spring.volgin.locale.LocalHolder;
import ru.otus.spring.volgin.validator.QuestionValidator;

import java.util.List;
import java.util.Locale;

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
     * Настройки экзамена
     */
    private final ExamApplicationProperties examApplicationProperties;
    /**
     * Сервис по взаимодействию со студентом
     */
    private final InteractionService interactionService;
    /**
     * Компонент по работе с местоположением
     */
    private final LocalHolder localHolder;

    @Override
    public void execute() {
        Locale locale = interactionService.chooseLocale();
        localHolder.changeLocale(locale);
        List<Question> questions = questionDao.findAll();
        questionValidator.validate(questions);
        Exam exam = Exam.builder()
                .requiredCorrectAnswers(examApplicationProperties.getRequiredCorrectAnswers())
                .questionAttempts(examApplicationProperties.getQuestionAttempts())
                .shuffleAnswers(examApplicationProperties.isShuffleAnswers())
                .build();
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
