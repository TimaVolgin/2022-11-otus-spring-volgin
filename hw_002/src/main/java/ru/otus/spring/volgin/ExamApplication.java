package ru.otus.spring.volgin;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.otus.spring.volgin.config.Config;
import ru.otus.spring.volgin.service.ExamService;

public class ExamApplication {

    public static void main(String[] args) {
        try (AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(Config.class)) {
            ExamService examService = ctx.getBean(ExamService.class);
            examService.execute();
        }
    }
}
