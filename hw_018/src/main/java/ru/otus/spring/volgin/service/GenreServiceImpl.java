package ru.otus.spring.volgin.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.spring.volgin.domain.Genre;
import ru.otus.spring.volgin.dto.GenreDto;
import ru.otus.spring.volgin.exceptions.NotFoundException;
import ru.otus.spring.volgin.mapper.GenreMapper;
import ru.otus.spring.volgin.repository.GenreRepository;

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
    /** Конвертер для жанра */
    private final GenreMapper genreMapper;

    @HystrixCommand
    @Override
    public GenreDto save(String name) {
        var genre = Genre.builder()
                .name(name)
                .build();
        return genreMapper.toDto(genreRepository.save(genre));
    }

    @HystrixCommand
    @Override
    public List<GenreDto> findAll() {
        return genreMapper.toDto(genreRepository.findAll());
    }

    @HystrixCommand(ignoreExceptions = { NotFoundException.class })
    @Override
    public Genre findByName(String name) {
        return genreRepository.findByName(name)
                .orElseThrow(() -> new NotFoundException(format("Не найден жанр с именем {0}", name)));
    }

    @HystrixCommand(ignoreExceptions = { NotFoundException.class })
    @Override
    public Genre findById(long id) {
        return genreRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(format("Не найден жанр с идентификатором {0}", id)));
    }
}
