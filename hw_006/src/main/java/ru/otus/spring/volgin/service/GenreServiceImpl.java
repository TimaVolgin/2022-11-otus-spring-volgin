package ru.otus.spring.volgin.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.spring.volgin.domain.Genre;
import ru.otus.spring.volgin.repository.GenreRepository;

import javax.transaction.Transactional;
import java.util.List;

import static java.text.MessageFormat.format;

/**
 * Сервис для работы с жанрами
 */
@Service
@RequiredArgsConstructor
public class GenreServiceImpl implements GenreService {

    /** Репозиторий для работы с жанрами */
    private final GenreRepository genreRepository;

    @Transactional
    @Override
    public Genre save(String name) {
        var genre = Genre.builder()
                .name(name)
                .build();
        return genreRepository.save(genre);
    }

    @Override
    public List<Genre> findAll() {
        return genreRepository.findAll();
    }

    @Override
    public Genre findByName(String name) {
        return genreRepository.findByName(name)
                .orElseThrow(() -> new IllegalArgumentException(format("Не найден жанр с именем {0}", name)));
    }

    @Override
    public Genre findById(long id) {
        return genreRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(format("Не найден жанр с идентификатором {0}", id)));
    }
}
