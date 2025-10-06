package main.model.question;

import main.model.question.checker.MultipleChoiceAnswerChecker;

public class MultipleChoiceQuestion extends Question {
    
    // REQUIRES: maxMark >= 0
    // EFFECTS: constructs a multiple choice question with given maximum mark, question statement
    //          and correct answer
    public MultipleChoiceQuestion(int maxMark, String questionString, char questionAnswer) {
        super(maxMark, questionString, new MultipleChoiceAnswerChecker(questionAnswer));   
    }
}
