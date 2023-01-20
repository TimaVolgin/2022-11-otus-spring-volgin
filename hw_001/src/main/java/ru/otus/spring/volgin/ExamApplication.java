package ru.otus.spring.volgin;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.otus.spring.volgin.service.ExamService;

public class ExamApplication {

    public static void main(String[] args) {
        try (ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("/spring-context.xml")) {
            ExamService examService = ctx.getBean(ExamService.class);
            examService.printQuestions();
        }
    }
}
