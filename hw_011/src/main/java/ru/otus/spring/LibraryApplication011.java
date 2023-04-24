package ru.otus.spring;

import com.github.cloudyrock.spring.v5.EnableMongock;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

/**
 * Приложение для хранения информации о книгах в библиотеке
 * Смотреть на http://localhost:8080/#/
 */
@EnableMongock
@EnableMongoRepositories
@SpringBootApplication
public class LibraryApplication011 {

    public static void main(String[] args) {
        SpringApplication.run(LibraryApplication011.class, args);
    }
}
