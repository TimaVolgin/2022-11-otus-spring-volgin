package ru.otus.spring.volgin.service;

import ru.otus.spring.volgin.dto.AuthorDto;

import java.util.List;

/**
 * Контракт для работы с авторами
 */
public interface AuthorService {

    /**
     * Сохраняет автора
     * @param fio      ФИО автора
     * @param birthday день рождения
     * @return автор
     */
    AuthorDto save(String fio, String birthday);

    /**
     * Возвращает всех авторов
     * @return список всех авторов
     */
    List<AuthorDto> findAll();

    /**
     * Возвращает автора по идентификатору
     * @param id идентификатор автора
     * @return автор
     */
    AuthorDto findById(long id);

}
