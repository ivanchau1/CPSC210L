package main.model.question.checker;

public class MultipleChoiceAnswerChecker extends AnswerChecker {

    private char answer;

    // EFFECTS: constructs checker for given answer
    public MultipleChoiceAnswerChecker(char answer) {
        // 
        this.answer = answer;
    }

    @Override
    public boolean checkAnswer(String userResponse) {
        if (userResponse.length() == 1) {
            return String.valueOf(answer).equalsIgnoreCase(userResponse);
        }
        return false;
    }
}
