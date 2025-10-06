package main.ui;

import static main.ui.QuizRunner.print;
import static main.ui.QuizRunner.println;

import main.model.question.*;
import main.model.quiz.*;

// Builds a quiz based on user input
class QuizBuilder {
    private Quiz quiz;

    // EFFECTS: generates a list of questions and builds a quiz based on user input
    QuizBuilder() {
        QuestionList questionList = getQuestions();
        quiz = buildQuiz(questionList);
    }

    // EFFECTS: returns the quiz
    Quiz getQuiz() {
        return quiz;
    }

    // EFFECTS: returns a list of questions
    private QuestionList getQuestions() {
        print("Welcome to the quiz!\n");
        QuestionList questionList = new QuestionList();

        addShortAnswer(questionList);
        addTrueFalse(questionList);
        addMultipleChoice(questionList);

        return questionList;
    }

    // MODIFIES: questionList
    // EFFECTS: adds short answer questions to questionList
    private void addShortAnswer(QuestionList questionList) {
        Question q1 = new ShortAnswerQuestion(4, "What planet are we on?", "Earth");
        Question q2 = new ShortAnswerQuestion(4, "What country are we in?", "Canada");
        questionList.addQuestion(q1);
        questionList.addQuestion(q2);
    }

    // MODIFIES: questionList
    // EFFECTS: adds true/false questions to questionList
    private void addTrueFalse(QuestionList questionList) {
        Question q1 = new TrueFalseQuestion(2, "True or False: fruits and veggies are good for you.", true);
        Question q2 = new TrueFalseQuestion(2, "True or False: donuts are good for you.", false);
        questionList.addQuestion(q1);
        questionList.addQuestion(q2);
    }

    // MODIFIES: questionList
    // EFFECTS: add multiple choice question to questionList
    private void addMultipleChoice(QuestionList questionList) {
        // TODO: uncomment lines below to interactively check a multiple choice question
       StringBuilder sb = new StringBuilder();
       sb.append("What colour is the sky?\n");
       sb.append("a) red\n");
       sb.append("b) green\n");
       sb.append("c) blue\n");
       sb.append("d) none of the above\n");
       Question qn = new MultipleChoiceQuestion(3, sb.toString(), 'c');
       questionList.addQuestion(qn);
    }

    // EFFECTS: returns quiz built from questions in questionList
    private Quiz buildQuiz(QuestionList questionList) {
        Quiz quiz = null;

        do {
            println("Enter a number for the type of quiz:");
            println("1 : Instant feedback quiz.");
            // Uncomment lines below and corresponding line in getQuizType,
            // as you complete the implementation of each quiz type
            println("2 : Unlimited tries quiz.");
            println("3 : Decreasing marks quiz.");

            print(">>> ");
            String questionType = QuizRunner.getUserResponse();
            quiz = getQuizType(questionList, questionType);
        } while (quiz == null);
        return quiz;
    }

    // EFFECTS: returns quiz based on question type
    private Quiz getQuizType(QuestionList questionList, String questionType) {
        Quiz quiz = null;
        switch (questionType) {
            case "1":
                quiz = new InstantFeedbackQuiz(questionList);
                break;
            case "2":
                quiz = new UnlimitedTriesQuiz(questionList);     // uncomment
                break;
            case "3":
                quiz = new DecreasingMarksQuiz(questionList, 2);    // uncomment
                break;
        }
        return quiz;
    }
}
