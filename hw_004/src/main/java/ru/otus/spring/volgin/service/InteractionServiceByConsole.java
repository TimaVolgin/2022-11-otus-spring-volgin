package ru.otus.spring.volgin.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.spring.volgin.domain.Answer;
import ru.otus.spring.volgin.domain.Exam;
import ru.otus.spring.volgin.domain.Question;
import ru.otus.spring.volgin.domain.Student;

/**
 * Класс взаимодействия со студентом через консоль ввода/вывода
 */
@Service
@RequiredArgsConstructor
public class InteractionServiceByConsole implements InteractionService {

    /**
     * Сервис ввода/вывода информации
     */
    private final IOService ioService;
    /**
     * Сервис по работе с сообщениями
     */
    private final MessageService messageService;
    /**
     * Сервис правил экзамена
     */
    private final ExamRulesService examRulesService;

    @Override
    public void askQuestion(Question question, boolean shuffle) {
        ioService.printDelimiter();
        ioService.print(messageService.getMessage("exam.question") + question.getText());
        ioService.print(messageService.getMessage("exam.question.answers"));
        if (shuffle) {
            examRulesService.shuffleAnswers(question);
        }
        for (int answerIndex = 0; answerIndex < question.getAnswers().size(); answerIndex++) {
            String answer = question.getAnswers().get(answerIndex);
            ioService.print(messageService.getMessage("exam.chooser_pattern", answerIndex, answer));
        }
    }

    @Override
    public Answer getAnswer(Question question, int attempts) {
        int answer = readAnswer(question);
        if (answer != question.getCorrectAnswerIndex() && attempts > 1) {
            attempts--;
            ioService.printError(messageService.getMessage("exam.answer.try_again", attempts));
            return getAnswer(question, attempts);
        }
        return Answer.builder().question(question).answer(answer).build();
    }

    @Override
    public void printResult(Exam exam) {
        ioService.printDelimiter();
        Student student = exam.getStudent();
        ioService.print(messageService.getMessage("exam.result.user", student.getFirstName(), student.getLastName(),
                student.getAge() == -1 ? "not specified" : student.getAge()));
        ioService.print(messageService.getMessage("exam.result.answers", examRulesService.getCorrectAnswerCount(exam), exam.getQuestions().size()));
        if (examRulesService.isExamPassed(exam)) {
            ioService.print(messageService.getMessage("exam.result.passed"));
        } else {
            ioService.printError(messageService.getMessage("exam.result.not_passed"));
        }
    }

    private int readAnswer(Question question) {
        ioService.print(messageService.getMessage("exam.answer.write"));
        int answer = ioService.readIntAnswer();
        int answersCount = question.getAnswers().size();
        if (answer >= answersCount || answer < 0) {
            ioService.printError(messageService.getMessage("exam.answer.incorrect",answersCount - 1));
            return readAnswer(question);
        }
        return answer;
    }
}
