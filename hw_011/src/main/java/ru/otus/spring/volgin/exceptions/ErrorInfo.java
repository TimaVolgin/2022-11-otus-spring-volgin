package ru.otus.spring.volgin.exceptions;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class ErrorInfo {

    private final String status;
    private final String message;
}

