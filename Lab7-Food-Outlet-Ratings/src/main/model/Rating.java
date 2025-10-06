package model;

import org.json.JSONObject;

// Represents a rating for a product that includes a comment and an
// integer score in the range 1 to 5, inclusive
public class Rating {
    private String comment;
    private int score;

    // REQUIRES: 1 <= score <= 5
    // EFFECTS: constructs a rating with given comment and score
    public Rating(String comment, int score) {
        this.comment = comment;
        this.score = score;
    }

    public String getComment() {
        return comment;
    }

    public int getScore() {
        return score;
    }

    public JSONObject toJson() {
        JSONObject ratingJson = new JSONObject();
        ratingJson.put("score", getScore());
        ratingJson.put("comment", getComment());
        return ratingJson;
    }
}
