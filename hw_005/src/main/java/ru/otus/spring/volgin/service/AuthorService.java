package ru.otus.spring.volgin.service;

import ru.otus.spring.volgin.domain.Author;

import java.util.List;

/**
 * Контракт по работе с авторами
 */
public interface AuthorService {

    /**
     * Сохраняет автора
     * @param fio     ФИО автора
     * @param birthday день рождения
     * @return автор
     */
    Author save(String fio, String birthday);

    /**
     * Возвращает всех авторов
     * @return список всех авторов
     */
    List<Author> findAll();

    /**
     * Возвращает автора по идентификатору
     * @param id идентификатор автора
     * @return автор
     */
    Author findById(long id);

}
