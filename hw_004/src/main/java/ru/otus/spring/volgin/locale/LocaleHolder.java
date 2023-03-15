package ru.otus.spring.volgin.locale;

import java.util.Locale;

/**
 * Контракт работы с местонахождением
 */
public interface LocaleHolder {

    /**
     * Изменяет местонахождение
     * @param locale местонахождение
     */
    void changeLocale(Locale locale);

    /**
     * Возвращает текущее местонахождение
     * @return местонахождение
     */
    Locale getLocale();

}
