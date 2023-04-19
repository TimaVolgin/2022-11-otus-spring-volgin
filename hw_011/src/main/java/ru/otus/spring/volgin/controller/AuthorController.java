package ru.otus.spring.volgin.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import ru.otus.spring.volgin.dto.AuthorDto;
import ru.otus.spring.volgin.mapper.AuthorMapper;
import ru.otus.spring.volgin.repository.AuthorRepository;

/**
 * Контроллер работы с авторами
 */
@RestController
@RequiredArgsConstructor
public class AuthorController {

    /** Сервис работы с авторами */
    private final AuthorRepository authorRepository;
    /** Маппер для работы с авторами */
    private final AuthorMapper authorMapper;

    /**
     * Возвращает список авторов
     * @return
     */
    @GetMapping("/authors")
    public Flux<AuthorDto> getAuthors() {
        return authorRepository.findAll()
                .map(authorMapper::toDto);
    }
}
