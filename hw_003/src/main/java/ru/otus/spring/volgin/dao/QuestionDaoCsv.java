package ru.otus.spring.volgin.dao;

import com.opencsv.bean.CsvToBeanBuilder;
import org.springframework.stereotype.Repository;
import ru.otus.spring.volgin.config.ExamApplicationProperties;
import ru.otus.spring.volgin.domain.Question;
import ru.otus.spring.volgin.locale.LocalHolder;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;
import java.util.Objects;

/**
 * Класс по работе с вопросами тестирования
 * Считывает вопросы из CSV ресурса {@link QuestionDaoCsv#scvResourceName}
 * В качестве разделителя используется {@link QuestionDaoCsv#CSV_SEPARATOR}
 */
@Repository
public class QuestionDaoCsv implements QuestionDao {

    /**
     * Разделитель в CSV файле информации по вопросу на составляющие элементы
     */
    private static final char CSV_SEPARATOR = ';';
    /**
     * Путь до ресурса, содержащего вопросы тестирования
     */
    private final String scvResourceName;
    /**
     * Компонент работы с местонахождением
     */
    private final LocalHolder localHolder;

    public QuestionDaoCsv(ExamApplicationProperties properties, LocalHolder localHolder) {
        this.scvResourceName = properties.getFilename();
        this.localHolder = localHolder;
    }

    @Override
    public List<Question> findAll() {
        String resourcePath = "/questions_" + localHolder.getLocale() + "/" + scvResourceName;
        try (Reader reader = new InputStreamReader(Objects.requireNonNull(
            this.getClass().getResourceAsStream(resourcePath), "Не найден файл с вопросами"))) {
            return new CsvToBeanBuilder<Question>(reader)
                    .withType(Question.class)
                    .withSeparator(CSV_SEPARATOR)
                    .build()
                    .parse();
        } catch (IOException e) {
            throw new RuntimeException("Ошибка чтения файла с вопросами", e);
        }
    }
}
