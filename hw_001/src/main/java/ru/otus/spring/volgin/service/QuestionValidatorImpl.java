package ru.otus.spring.volgin.service;

import ru.otus.spring.volgin.domain.Question;

import java.util.List;

import static java.text.MessageFormat.format;

/**
 * Сервис проверки списка вопросов на соответствие необходимым требованиям
 */
public class QuestionValidatorImpl implements QuestionValidator {

    @Override
    public void validate(List<Question> questionList) {
        questionList.forEach(question -> {
            if (question.getAnswers() == null || question.getAnswers().size() < 2) {
                throw new IllegalStateException(format("У вопроса \"{0}\" должно быть несколько вариантов ответа", question.getQuestionText()));
            }
            if (question.getAnswers().size() - 1 < question.getCorrectAnswerIndex() || question.getCorrectAnswerIndex() < 0) {
                throw new IllegalStateException(format("У вопроса \"{0}\" указан неверный индекс ответа", question.getQuestionText()));
            }
        });
    }
}
