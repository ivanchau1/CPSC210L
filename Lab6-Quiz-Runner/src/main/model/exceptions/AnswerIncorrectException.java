package main.model.exceptions;

public class AnswerIncorrectException extends QuizRunnerException {
    public AnswerIncorrectException(String msg) {
        super(msg);
    }
}
