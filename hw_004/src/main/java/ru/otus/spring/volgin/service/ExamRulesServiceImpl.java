package ru.otus.spring.volgin.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.spring.volgin.domain.Answer;
import ru.otus.spring.volgin.domain.Exam;
import ru.otus.spring.volgin.domain.Question;

import java.util.Collections;
import java.util.List;

/**
 * Сервис правил экзамена
 */
@Service
@RequiredArgsConstructor
public class ExamRulesServiceImpl implements ExamRulesService {

    @Override
    public long getCorrectAnswerCount(Exam exam) {
        return exam.getStudentAnswers().stream()
                .filter(this::isRightAnswer)
                .count();
    }

    @Override
    public boolean isExamPassed(Exam exam) {
        return exam.getRequiredCorrectAnswers() <= getCorrectAnswerCount(exam);
    }

    @Override
    public boolean isRightAnswer(Answer answer) {
        return answer.getQuestion().getCorrectAnswerIndex() == answer.getAnswer();
    }

    @Override
    public void shuffleAnswers(Question question) {
        List<String> answers = question.getAnswers();
        String rightAnswer = answers.get(question.getCorrectAnswerIndex());
        Collections.shuffle(answers);
        question.setCorrectAnswerIndex(answers.indexOf(rightAnswer));
    }
}
