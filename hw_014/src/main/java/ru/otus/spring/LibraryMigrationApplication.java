package ru.otus.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

/**
 * Приложение для хранения информации о книгах в библиотеке
 */
@EnableMongoRepositories
@SpringBootApplication
public class LibraryMigrationApplication {

    public static void main(String[] args) {
        SpringApplication.run(LibraryMigrationApplication.class, args);
    }
}
