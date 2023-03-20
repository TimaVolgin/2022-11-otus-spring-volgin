package ru.otus.spring.volgin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import ru.otus.spring.volgin.config.ExamApplicationProperties;

@SpringBootApplication
@EnableConfigurationProperties(ExamApplicationProperties.class)
public class ExamShellApplication {

    public static void main(String[] args) {
        SpringApplication.run(ExamShellApplication.class, args);
    }
}
