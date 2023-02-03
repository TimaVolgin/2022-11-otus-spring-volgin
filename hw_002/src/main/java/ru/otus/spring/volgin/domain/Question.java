package ru.otus.spring.volgin.domain;

import com.opencsv.bean.CsvBindAndSplitByName;
import com.opencsv.bean.CsvBindByName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Вопрос тестирования
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Question {
    /**
     * Текст вопроса
     */
    @CsvBindByName(column = "questionText", required = true)
    private String text;
    /**
     * Варианы ответов
     */
    @CsvBindAndSplitByName(elementType = String.class, collectionType = ArrayList.class, splitOn = "\\,", required = true)
    private List<String> answers;
    /**
     * Индекс верного ответа
     */
    @CsvBindByName(required = true)
    private int correctAnswerIndex;

    /**
     * Перемешивает ответы на вопросы
     */
    public void shuffleAnswers() {
        String rightAnswer = answers.get(correctAnswerIndex);
        Collections.shuffle(answers);
        correctAnswerIndex = answers.indexOf(rightAnswer);
    }
}
