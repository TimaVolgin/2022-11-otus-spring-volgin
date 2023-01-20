package ru.otus.spring.volgin.domain;

import com.opencsv.bean.CsvBindAndSplitByName;
import com.opencsv.bean.CsvBindByName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
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
    @CsvBindByName(required = true)
    private String questionText;
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
}
