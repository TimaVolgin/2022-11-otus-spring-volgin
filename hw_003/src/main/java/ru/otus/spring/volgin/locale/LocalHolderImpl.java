package ru.otus.spring.volgin.locale;

import org.springframework.stereotype.Component;
import ru.otus.spring.volgin.config.ExamApplicationProperties;

import java.util.Locale;

/**
 * Класс работы с местонахождением
 */
@Component
public class LocalHolderImpl implements LocalHolder {

    /**
     * Текущее местонахождение
     */
    private Locale locale;

    /**
     * Конструктор
     * @param properties свойства приложения
     */
    public LocalHolderImpl(ExamApplicationProperties properties) {
        this.locale = properties.getLocales().contains(Locale.getDefault()) ?
                Locale.getDefault() : properties.getLocales().get(0);
    }

    @Override
    public void changeLocale(Locale locale) {
        this.locale = locale;
    }

    @Override
    public Locale getLocale() {
        return locale;
    }
}
