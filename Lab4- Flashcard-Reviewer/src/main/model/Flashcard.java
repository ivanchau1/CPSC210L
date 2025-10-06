package model;

// A class representing a flashcard with a prompt, answer, and a review status
public class Flashcard {
    // TODO: figure out what fields your class should have!
    private boolean reviewStatus;
    private String prompt;
    private String answer;

    // EFFECTS: constructs an unreviewed Flashcard object
    public Flashcard(String prompt, String answer) {
        // TODO: implement the constructor!
        this.prompt = prompt;
        this.answer = answer;
        this.reviewStatus = false;
    }

    // EFFECTS: returns true if the given answer exactly matches this flashcard's answer (ie. case sensitive!)
    public boolean checkAnswer(String submittedAnswer) {
        // TODO: implement this method!
        return submittedAnswer.equals(this.answer);
    }

    // MODIFIES: this
    // EFFECTS: marks the flashcard as reviewed
    public void markAsReviewed() {
        // TODO: implement this method!
        reviewStatus = true;
    }

    // MODIFIES: this
    // EFFECTS: marks the flashcard as not reviewed
    public void markAsNotReviewed() {
        // TODO: implement this method!
        reviewStatus = false;
    }

    // MODIFIES: this
    // EFFECTS: sets the flashcard's prompt to the new, given prompt
    public void setPrompt(String newPrompt) {
        // TODO: implement this method!
        this.prompt = newPrompt;
    }

    // MODIFIES: this
    // EFFECTS: sets the flashcard's answer to the new, given answer
    public void setAnswer(String newAnswer) {
        // TODO: implement this method!
        this.answer = newAnswer;
    }

    // EFFECTS: returns the flashcard's prompt
    public String getPrompt() {
        // TODO: implement this method!
        return prompt;
    }

    // EFFECTS: returns the flashcard's answer
    public String getAnswer() {
        // TODO: implement this method!
        return answer;
    }

    // EFFECTS: returns the flashcard's review status
    public boolean getReviewStatus() {
        // TODO: implement this method!
        return reviewStatus;
    }
}
