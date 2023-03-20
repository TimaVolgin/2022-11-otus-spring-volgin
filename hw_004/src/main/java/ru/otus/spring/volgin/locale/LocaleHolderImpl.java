package ru.otus.spring.volgin.locale;

import org.springframework.stereotype.Component;
import ru.otus.spring.volgin.config.ExamApplicationProperties;

import java.util.List;
import java.util.Locale;

import static java.text.MessageFormat.format;

/**
 * Класс работы с местонахождением
 */
@Component
public class LocaleHolderImpl implements LocaleHolder {

    /**
     * Список доступных местонахождений
     */
    private final List<Locale> availableLocales;

    /**
     * Текущее местонахождение
     */
    private Locale locale;

    /**
     * Конструктор
     * @param properties свойства приложения
     */
    public LocaleHolderImpl(ExamApplicationProperties properties) {
        this.availableLocales = properties.getLocales();
        this.locale = availableLocales.contains(Locale.getDefault()) ?
                Locale.getDefault() : availableLocales.get(0);
    }

    @Override
    public void changeLocale(Locale locale) {
        if (!availableLocales.contains(locale)) {
            throw new IllegalArgumentException(format("Locale {0} not supported", locale));
        }
        this.locale = locale;
    }

    @Override
    public Locale getLocale() {
        return locale;
    }
}
