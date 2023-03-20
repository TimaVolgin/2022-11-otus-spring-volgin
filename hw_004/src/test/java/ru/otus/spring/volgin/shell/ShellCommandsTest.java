package ru.otus.spring.volgin.shell;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.shell.CommandNotCurrentlyAvailable;
import org.springframework.shell.ParameterMissingResolutionException;
import org.springframework.shell.Shell;
import ru.otus.spring.volgin.locale.LocaleHolder;
import ru.otus.spring.volgin.service.ExamService;
import ru.otus.spring.volgin.service.MessageService;

import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.only;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.internal.verification.VerificationModeFactory.noInteractions;

@DisplayName("Тест shell команд")
@SpringBootTest
class ShellCommandsTest {

    @SpyBean
    private LocaleHolder localeHolder;
    @MockBean
    private ExamService examService;
    @MockBean
    private MessageService messageService;

    @Autowired
    private Shell shell;

    @DisplayName("Тест команды смены языка")
    @Test
    void changeLanguage() {
        shell.evaluate(() -> "lang ru");
        assertEquals(new Locale("ru"), localeHolder.getLocale());
        verify(localeHolder).changeLocale(new Locale("ru"));
        shell.evaluate(() -> "lang en");
        assertEquals(new Locale("en"), localeHolder.getLocale());
        verify(localeHolder).changeLocale(new Locale("en"));
        verify(messageService, times(2)).getMessage("exam.shell.language_changed");
        shell.evaluate(() -> "lang spain");
        verify(localeHolder).changeLocale(new Locale("spain"));
        verify(messageService, times(2)).getMessage("exam.shell.language_changed");
        assertEquals(new Locale("en"), localeHolder.getLocale());
    }

    @DisplayName("Тест запуска экзамена")
    @Test
    void startExam() {
        assertTrue(shell.evaluate(() -> "start") instanceof CommandNotCurrentlyAvailable);
        verify(examService, noInteractions()).execute(any());
        assertTrue(shell.evaluate(() -> "st") instanceof ParameterMissingResolutionException);
        assertTrue(shell.evaluate(() -> "st Ivan") instanceof ParameterMissingResolutionException);
        shell.evaluate(() -> "st Ivan Ivanov 33");
        shell.evaluate(() -> "start");
        verify(examService, only()).execute(any());
    }
}
