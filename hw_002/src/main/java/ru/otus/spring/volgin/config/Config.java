package ru.otus.spring.volgin.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import ru.otus.spring.volgin.service.IOService;
import ru.otus.spring.volgin.service.IOServiceSteam;

/**
 * Конфигурация приложения
 */
@Configuration
@ComponentScan("ru.otus.spring.volgin")
@PropertySource("classpath:application.properties")
public class Config {

    @Bean
    public IOService getIOService() {
        return new IOServiceSteam(System.out, System.err, System.in);
    }
}
