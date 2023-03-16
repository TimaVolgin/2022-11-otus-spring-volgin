package ru.otus.spring.volgin.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.spring.volgin.dao.AuthorDao;
import ru.otus.spring.volgin.domain.Author;

import java.time.LocalDate;
import java.util.List;

import static java.text.MessageFormat.format;

/**
 * Сервис по работе с авторами
 */
@Service
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {

    /** DAO По работе с авторами */
    private final AuthorDao authorDao;

    @Override
    public Author save(String fio, String birthday) {
        return authorDao.save(Author.builder()
                .fio(fio)
                .birthday(LocalDate.parse(birthday))
                .build());
    }

    @Override
    public List<Author> findAll() {
        return authorDao.findAll();
    }

    @Override
    public Author findById(long id) {
        return authorDao.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(format("Не найден автор с идентификатором {0}", id)));
    }
}
