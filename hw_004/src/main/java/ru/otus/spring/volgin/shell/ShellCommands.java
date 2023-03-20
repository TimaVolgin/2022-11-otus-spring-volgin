package ru.otus.spring.volgin.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;
import org.springframework.shell.standard.ShellOption;
import ru.otus.spring.volgin.config.ExamApplicationProperties;
import ru.otus.spring.volgin.domain.Exam;
import ru.otus.spring.volgin.domain.Student;
import ru.otus.spring.volgin.locale.LocaleHolder;
import ru.otus.spring.volgin.service.ExamService;
import ru.otus.spring.volgin.service.MessageService;

import java.util.Locale;


/**
 * Shell команды приложения
 */
@ShellComponent
@RequiredArgsConstructor
public class ShellCommands {

    /** Компонент по работе с местонахождением */
    private final LocaleHolder localeHolder;
    /** Сервис по работе с сообщениями */
    private final MessageService messageService;
    /** Сервис по работе с тестирование */
    private final ExamService examService;
    /** Настройки приложения */
    private final ExamApplicationProperties examApplicationProperties;
    /** Экзаменуемый пользователь */
    private Student student;

    /**
     * Изменяет используемое местоположение при тестировании
     * @param locale местоположение
     * @return сообщение о результате изменения местоположения
     */
    @ShellMethod(value = "Change language", key = {"lang", "language"})
    public String changeLanguage(@ShellOption(defaultValue = "en") Locale locale) {
        try {
            localeHolder.changeLocale(locale);
            return messageService.getMessage("exam.shell.language_changed");
        } catch (IllegalArgumentException exception) {
            return exception.getMessage();
        }
    }

    /**
     * Устанавливает экзаменуемого
     * @param firstName имя студента
     * @param lastName  фамилия студента
     * @param age       возраст студента
     */
    @ShellMethod(value = "Set Student info command", key = {"st", "student"})
    public void student(@ShellOption String firstName, @ShellOption String lastName, @ShellOption int age) {
        this.student = Student.builder().firstName(firstName).lastName(lastName).age(age).build();
    }

    /**
     * Запускает экзамен. Экзамен нельзя запустить без экзаменуемого
     */
    @ShellMethod(value = "start", key = {"start"})
    @ShellMethodAvailability(value = "isExamAvailable")
    public void startExam() {
        var exam = Exam.builder()
                .requiredCorrectAnswers(examApplicationProperties.getRequiredCorrectAnswers())
                .shuffleAnswers(examApplicationProperties.isShuffleAnswers())
                .questionAttempts(examApplicationProperties.getQuestionAttempts())
                .student(student)
                .build();
        examService.execute(exam);
    }

    /**
     * Возвращает информацию о доступности экзамена
     * @return информация о доступности экзамена
     */
    private Availability isExamAvailable() {
        return student == null ? Availability.unavailable("Сначала введите Фамилию и Имя экзаменуемого") : Availability.available();
    }
}
