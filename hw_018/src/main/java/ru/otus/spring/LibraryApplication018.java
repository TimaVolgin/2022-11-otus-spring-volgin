package ru.otus.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;

/**
 * Приложение для хранения информации о книгах в библиотеке
 */
@EnableCircuitBreaker
@SpringBootApplication
public class LibraryApplication018 {

    public static void main(String[] args) {
        SpringApplication.run(LibraryApplication018.class, args);
    }
}
