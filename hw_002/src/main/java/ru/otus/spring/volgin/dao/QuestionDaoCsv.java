package ru.otus.spring.volgin.dao;

import com.opencsv.bean.CsvToBeanBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import ru.otus.spring.volgin.domain.Question;

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

    public QuestionDaoCsv(@Value("${exam.filename}")String scvResourceName) {
        this.scvResourceName = scvResourceName;
    }

    @Override
    public List<Question> findAll() {
        try (Reader reader = new InputStreamReader(Objects.requireNonNull(
            this.getClass().getResourceAsStream("/" + scvResourceName), "Не найден файл с вопросами"))) {
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
