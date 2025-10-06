package main.model.quiz;

import main.model.exceptions.*;
import main.model.question.Question;
import main.model.question.QuestionList;

public class DecreasingMarksQuiz extends Quiz {

    private int maxAttempts;
    private int remainingAttempts;

    // REQUIRES: questions cannot be an empty list, maxAttempts >= 1
    // EFFECTS: constructs quiz with given list of questions and no more than maxAttempts
    // attempts to answer a question
    public DecreasingMarksQuiz(QuestionList questions, int maxAttempts) {
        super(questions);
        this.maxAttempts = maxAttempts;
        this.remainingAttempts = maxAttempts;
    }

    // EFFECTS: returns number of remaining attempts to answer current question in this quiz
    public int getRemainingAttempts() {
        return remainingAttempts;  
    }

    public int getMaxAttempts() {
        return maxAttempts;  
    }

    // REQUIRES: hasMoreQuestions()
    // MODIFIES: this
    // EFFECTS: resets number of remaining attempts to maximum attempts and returns next question in quiz
    @Override
    public Question getNextQuestion() {
        remainingAttempts = getMaxAttempts();
        return super.getNextQuestion();
    }

    // MODIFIES: this
    // EFFECTS:
    //          if all attempts to answer the question have been used up, throws OutOfTriesException
    //          otherwise submit an answer to the current question and decrease number of attempts by 1
    //          if answer is correct:
    //              - return feedback string "Correct!"
    //          if the answer is incorrect:
    //              - decrements the max mark of the current question by one, while
    //                ensuring that the max mark does not drop below zero
    //              - if user has now used up all attempts to answer the question, throws OutOfTriesException
    //              - otherwise if the user should re-try the question, throws AnswerIncorrectException
    @Override
    public String submitAnswer(String answer) throws AnswerIncorrectException, OutOfTriesException {
        int maxMark = curQuestion.getMaxMark();
        if (getRemainingAttempts() <= 0) {
            throw new OutOfTriesException("Out of Tries!");
        } else {
            return isCorrect(answer, maxMark);
        }
    }

    // MODIFIES: this
    // EFFECTS: 
    //          if answer is correct:
    //              - return feedback string "Correct!"
    //          if the answer is incorrect:
    //              - decrements the max mark of the current question by one, while
    //                ensuring that the max mark does not drop below zero
    //              - if user has now used up all attempts to answer the question, throws OutOfTriesException
    //              - otherwise if the user should re-try the question, throws AnswerIncorrectException
    public String isCorrect(String answer, int maxMark) throws AnswerIncorrectException, OutOfTriesException {
        remainingAttempts--;
        if (checkAnswer(answer)) {
            curQuestion.setMaxMark(maxMark);
            return "Correct!";
        } else {
            if (maxMark > 0) {
                curQuestion.setMaxMark(maxMark - 1);
            }
            if (getRemainingAttempts() <= 0) {
                throw new OutOfTriesException("Out of attempts!");
            } else {
                throw new AnswerIncorrectException("Incorrect!");
            }
        }
    }
}
