package ru.otus.spring.volgin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ConfigurableApplicationContext;
import ru.otus.spring.volgin.config.ExamApplicationProperties;
import ru.otus.spring.volgin.service.ExamService;

@SpringBootApplication
@EnableConfigurationProperties(ExamApplicationProperties.class)
public class ExamApplication {

    public static void main(String[] args) {
        try (ConfigurableApplicationContext ctx = SpringApplication.run(ExamApplication.class, args)) {
            ctx.getBean(ExamService.class).execute();
        }
    }
}
