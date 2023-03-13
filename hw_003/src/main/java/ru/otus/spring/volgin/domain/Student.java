package ru.otus.spring.volgin.domain;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Student {
    /**
     * Имя студента
     */
    private final String firstName;

    /**
     * Фамилия студента
     */
    private final String lastName;

    /**
     * Возраст студента
     */
    private final int age;
}
