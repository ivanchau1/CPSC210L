package persistence;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;
import model.FoodOutlet;
import model.FoodOutletRatings;
import model.Rating;
import org.json.JSONArray;
import org.json.JSONObject;

// Represents a reader that reads food outlets from JSON data stored in file
public class JsonReader {
    private String location;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String location) {
        this.location = location;
    }

    // EFFECTS: parses stored JSON file and constructs a FoodOutletRatings object
    // throws IOException if the source cannot be read
    public FoodOutletRatings read() throws IOException {
        Path path = Paths.get(location);
        if (!Files.exists(path)) {
            throw new IOException("File not found: " + location);
        }

        StringBuilder jsonContent = new StringBuilder();
        try (Stream<String> stream = Files.lines(path, StandardCharsets.UTF_8)) {
            stream.forEach(jsonContent::append);
            JSONArray jsonArray = new JSONArray(jsonContent.toString());
            return parseFoodOutletRatings(jsonArray);
        }
    }

    // EFFECTS: parses JSON array and constructs a FoodOutletRatings object
    private FoodOutletRatings parseFoodOutletRatings(JSONArray jsonArray) {
        FoodOutletRatings ratings = new FoodOutletRatings();
        for (Object outletObject : jsonArray) {
            JSONObject outletAndRatings = (JSONObject) outletObject;
            FoodOutlet outlet = parseFoodOutlet(outletAndRatings.getJSONObject("food_outlet"));
            for (Object ratingObject : outletAndRatings.getJSONArray("ratings")) {
                JSONObject rating = (JSONObject) ratingObject;
                ratings.addRating(outlet, parseRating(rating));
            }
        }
        return ratings;
    }

    // EFFECTS: parses JSON object and constructs a FoodOutlet object
    private FoodOutlet parseFoodOutlet(JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        String location = jsonObject.getString("location");
        return new FoodOutlet(name, location);
    }

    // EFFECTS: parses JSON object and constructs a Rating object
    private Rating parseRating(JSONObject jsonRating) {
        int score = jsonRating.getInt("score");
        String comment = jsonRating.getString("comment");
        return new Rating(comment, score);
    }
}
