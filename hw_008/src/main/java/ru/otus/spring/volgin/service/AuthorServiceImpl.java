package ru.otus.spring.volgin.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.spring.volgin.domain.Author;
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

    @Override
    public Author save(String name, String birthday) {
        var author = Author.builder()
                .fio(name)
                .birthday(LocalDate.parse(birthday))
                .build();
        return authorRepository.save(author);
    }

    @Override
    public List<Author> findAll() {
        return authorRepository.findAll();
    }

    @Override
    public Author findById(String id) {
        return authorRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(format("Не найден автор с идентификатором {0}", id)));
    }
}
