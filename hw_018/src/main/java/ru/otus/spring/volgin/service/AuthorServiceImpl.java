package ru.otus.spring.volgin.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.spring.volgin.domain.Author;
import ru.otus.spring.volgin.dto.AuthorDto;
import ru.otus.spring.volgin.exceptions.NotFoundException;
import ru.otus.spring.volgin.mapper.AuthorMapper;
import ru.otus.spring.volgin.repository.AuthorRepository;

import java.time.LocalDate;
import java.util.List;

import static java.text.MessageFormat.format;

/**
 * Сервис для работы с авторами
 */
@Service
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {

    /** Репозиторий для работы с авторами */
    private final AuthorRepository authorRepository;
    /** Конвертор для автора */
    private final AuthorMapper authorMapper;

    @HystrixCommand
    @Override
    public AuthorDto save(String name, String birthday) {
        var author = Author.builder()
                .fio(name)
                .birthday(LocalDate.parse(birthday))
                .build();
        return authorMapper.toDto(authorRepository.save(author));
    }

    @HystrixCommand
    @Override
    public List<AuthorDto> findAll() {
        return authorMapper.toDto(authorRepository.findAll());
    }

    @HystrixCommand(ignoreExceptions = { NotFoundException.class })
    @Override
    public AuthorDto findById(long id) {
        return authorMapper.toDto(authorRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(format("Не найден автор с идентификатором {0}", id))));
    }
}
