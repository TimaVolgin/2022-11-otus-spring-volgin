package ru.otus.spring.volgin.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.otus.spring.volgin.service.IOService;
import ru.otus.spring.volgin.service.IOServiceSteam;
import ru.otus.spring.volgin.service.MessageService;

/**
 * Конфигурация приложения
 */
@Configuration
public class ExamApplicationConfig {

    @Bean
    public IOService getIOService(MessageService messageService) {
        return new IOServiceSteam(System.out, System.err, System.in, messageService);
    }
}
