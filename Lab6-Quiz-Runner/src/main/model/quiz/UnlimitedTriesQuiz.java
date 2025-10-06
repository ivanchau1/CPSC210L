package main.model.quiz;

import main.model.exceptions.*;
import main.model.question.QuestionList;

public class UnlimitedTriesQuiz extends Quiz {
    
    private int numAttempts;
    private QuestionList questions;

    // REQUIRES: questions cannot be an empty list
    // EFFECTS: constructs quiz with given list of questions
    public UnlimitedTriesQuiz(QuestionList questions) {
        super(questions);
        this.questions = questions;
        numAttempts = 0;
    }
    
    // MODIFIES: this
    // EFFECTS: submit an answer to the current question and return feedback string;
    // does not modify max mark of current question;
    // throws AnswerIncorrectException if the user should re-try the question;
    @Override
    public String submitAnswer(String answer) throws AnswerIncorrectException {
        numAttempts++;
        if (!super.checkAnswer(answer)) {
            throw new AnswerIncorrectException("Incorrect!");
        }
        return "Correct!";
    }

    // EFFECTS: returns number of attempts taken to answer questions so far
    public int getNumAttempts() {
        return numAttempts;
    }

    @Override
    // EFFECTS: returns a string providing feedback to the user on their performance in the quiz
    public String endQuiz() {
        int numAttempts = getNumAttempts();
        int numQuestions = questions.length();
        return "It took you " + numAttempts + " attempts to answer " + numQuestions + " questions correctly.";  
    }

}
