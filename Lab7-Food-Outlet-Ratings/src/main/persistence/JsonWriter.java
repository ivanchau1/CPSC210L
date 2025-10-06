package persistence;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import model.FoodOutletRatings;
import org.json.JSONArray;

// Represents a writer that writes JSON representation of FoodOutletRatings to file
public class JsonWriter {
    private static final int INDENTATION_LEVEL = 2;
    private String location;

    // EFFECTS: constructs a JsonWriter which writes to the file at location
    public JsonWriter(String location) {
        this.location = location;
    }

    // MODIFIES: this
    // EFFECTS: writes the JSON representation of the given FoodOutletRatings
    // to the file located at location
    // throws FileNotFoundException if the file cannot be found or created
    public void write(FoodOutletRatings ratings) throws FileNotFoundException {
        PrintWriter writer = new PrintWriter(location);
        JSONArray ratingsJson = ratings.toJson();
        writer.print(ratingsJson.toString(INDENTATION_LEVEL));
        writer.close();
    }
}
