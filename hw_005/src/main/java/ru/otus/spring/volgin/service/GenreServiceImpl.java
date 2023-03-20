package ru.otus.spring.volgin.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.spring.volgin.dao.GenreDao;
import ru.otus.spring.volgin.domain.Genre;

import java.util.List;
import java.util.Optional;

import static java.text.MessageFormat.format;

/**
 * Сервис по работе с жанрами
 */
@Service
@RequiredArgsConstructor
public class GenreServiceImpl implements GenreService {

    /** DAO по работе с жанрами */
    private final GenreDao genreDao;

    @Override
    public Genre save(String name) {
        return genreDao.save(Genre.builder()
                .name(name)
                .build());
    }

    @Override
    public List<Genre> findAll() {
        return genreDao.findAll();
    }

    @Override
    public Optional<Genre> findByName(String name) {
        return genreDao.findByName(name);
    }

    @Override
    public Genre findById(long id) {
        return genreDao.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(format("Не найден жанр с идентификатором {0}", id)));
    }
}
