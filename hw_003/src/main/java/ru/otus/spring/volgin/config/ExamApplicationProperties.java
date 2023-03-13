package ru.otus.spring.volgin.config;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

import java.util.List;
import java.util.Locale;

/**
 * Настройки приложения
 */
@Getter
@AllArgsConstructor
@ConstructorBinding
@ConfigurationProperties(prefix="exam-application")
public class ExamApplicationProperties {

    /**
     * Имя файла с вопросами
     */
    private final String filename;

    /**
     * Количество необходимых правильных ответов
     */
    private final int requiredCorrectAnswers;

    /**
     * Количество попыток ответа на вопрос
     */
    private final int questionAttempts;

    /**
     * Признак того, что ответы на вопрос будут перемешаны
     */
    private final boolean shuffleAnswers;

    /**
     * Доступные локализации
     */
    private final List<Locale> locales;
}
