package ru.otus.spring.volgin.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;
import ru.otus.spring.volgin.service.JobService;

@ShellComponent
@RequiredArgsConstructor
public class ShellCommands {
    private final JobService jobService;

    @ShellMethod(value = "Запуск миграции", key = "run")
    @ShellMethodAvailability(value = "isAvailableToRun")
    public void startJob() throws Exception {
        jobService.run();
    }

    @ShellMethod(value = "Перезапуск миграции", key = "restart")
    @ShellMethodAvailability(value = "isAvailableToRestart")
    public void restartJob() throws Exception {
        jobService.restart();
    }

    @ShellMethod(value = "Показать информацию о задаче", key = "info")
    @ShellMethodAvailability(value = "isAvailableToRerun")
    public String showJobInfo() throws Exception {
        return jobService.showJobInfo();
    }
}
