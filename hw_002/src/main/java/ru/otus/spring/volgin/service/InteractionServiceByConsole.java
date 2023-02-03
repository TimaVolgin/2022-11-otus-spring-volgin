package ru.otus.spring.volgin.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.spring.volgin.domain.Answer;
import ru.otus.spring.volgin.domain.Exam;
import ru.otus.spring.volgin.domain.Question;
import ru.otus.spring.volgin.domain.Student;

import static java.text.MessageFormat.format;

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

    @Override
    public Student getStudentInfo() {
        ioService.print("Hello, student! Enter your firstName");
        String firstName = ioService.readAnswer();
        ioService.print("Enter your lastName");
        String lastName = ioService.readAnswer();
        ioService.print("Enter your age");
        int age = ioService.readIntAnswer();
        return Student.builder()
                .firstName(firstName)
                .lastName(lastName)
                .age(age)
                .build();
    }

    @Override
    public void askQuestion(Question question, boolean shuffle) {
        ioService.printDelimiter();
        ioService.print("Question: " + question.getText());
        ioService.print("Answers: ");
        if (shuffle) {
            question.shuffleAnswers();
        }
        for (int answerIndex = 0; answerIndex < question.getAnswers().size(); answerIndex++) {
            String answer = question.getAnswers().get(answerIndex);
            ioService.print(format("{0}. {1}", answerIndex, answer));
        }
    }

    @Override
    public Answer getAnswer(Question question, int attempts) {
        int answer = readAnswer(question);
        if (answer != question.getCorrectAnswerIndex() && attempts > 1) {
            attempts--;
            ioService.printError(format("Wrong Answer. Remaining attempts: {0}. Try again.", attempts));
            return getAnswer(question, attempts);
        }
        return Answer.builder().question(question).answer(answer).build();
    }

    @Override
    public void printResult(Exam exam) {
        ioService.printDelimiter();
        Student student = exam.getStudent();
        ioService.print(format("Student: {0} {1}, age {2}", student.getFirstName(), student.getLastName(),
                student.getAge() == -1 ? "not specified" : student.getAge()));
        ioService.print(format("Right answers: {0}/{1}", exam.getCorrectAnswerCount(), exam.getQuestions().size()));
        if (exam.isExamPassed()) {
            ioService.print("PASSED");
        } else {
            ioService.printError("NOT PASSED");
        }
    }

    private int readAnswer(Question question) {
        ioService.print("Please, write your answer number");
        int answer = ioService.readIntAnswer();
        int answersCount = question.getAnswers().size();
        if (answer >= answersCount || answer < 0) {
            ioService.printError(format("The answer should be in the range from 0 to {0}. Try again", answersCount - 1));
            return readAnswer(question);
        }
        return answer;
    }
}
