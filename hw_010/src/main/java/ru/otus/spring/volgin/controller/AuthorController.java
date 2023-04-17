package ru.otus.spring.volgin.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.spring.volgin.dto.AuthorDto;
import ru.otus.spring.volgin.service.AuthorService;

import java.util.List;

/**
 * Контроллер работы с авторами
 */
@RestController
@RequiredArgsConstructor
public class AuthorController {

    /** Сервис работы с авторами */
    private final AuthorService authorService;

    /**
     * Возвращает список авторов
     * @return
     */
    @GetMapping("/authors")
    public List<AuthorDto> getAuthors() {
        return authorService.findAll();
    }
}
