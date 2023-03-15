package ru.otus.spring.volgin.service;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;

/**
 * Сервис ввода вывода информации
 */
public class IOServiceSteam implements IOService {

    /**
     * Поток вывода информации
     */
    private final PrintStream output;
    /**
     * Поток вывода информации об ошибках
     */
    private final PrintStream errorOutput;
    /**
     * Поток ввода
     */
    private final Scanner input;
    /**
     * Компонент локализаци
     * */
    private final MessageService messageService;

    /**
     * Конструктор
     * @param output      поток вывода информации
     * @param errorOutput поток вывода информации об ошибках
     * @param input       поток ввода
     */
    public IOServiceSteam(PrintStream output, PrintStream errorOutput, InputStream input, MessageService messageService) {
        this.output = output;
        this.errorOutput = errorOutput;
        this.input = new Scanner(input);
        this.messageService = messageService;
    }

    @Override
    public void print(String message) {
        output.println(message);
    }

    @Override
    public void printDelimiter() {
        print("-----------------------------------------------------------------");
    }

    @Override
    public void printError(String message) {
        errorOutput.println(message);
    }

    @Override
    public String readAnswer() {
        return input.nextLine();
    }

    @Override
    public int readIntAnswer() {
        try {
            return Integer.parseInt(readAnswer());
        } catch (NumberFormatException e) {
            printError(messageService.getMessage("exam.answer.read_int"));
            return readIntAnswer();
        }
    }
}
