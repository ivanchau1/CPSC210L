package persistence;

import model.FoodOutlet;
import model.FoodOutletRatings;
import model.Rating;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JsonConversionTest {
    @Test
    public void testFoodOutletRatings() {
        FoodOutletRatings outletRatings = new FoodOutletRatings();
        outletRatings.addRating(new FoodOutlet("Chipotle", "UBC"),
            new Rating("Great", 5));
        JSONArray serialized = outletRatings.toJson();
        assertEquals(1, serialized.length());
        JSONArray ratingsArray= serialized.getJSONObject(0).getJSONArray("ratings");
        assertEquals(1, ratingsArray.length());
        JSONObject ratingObject = ratingsArray.getJSONObject(0);
        assertEquals(5, ratingObject.getInt("score"));
        assertEquals("Great", ratingObject.getString("comment"));
        JSONObject foodOuletObject = serialized.getJSONObject(0).getJSONObject("food_outlet");
        assertEquals("Chipotle", foodOuletObject.getString("name"));
        assertEquals("UBC", foodOuletObject.getString("location"));
    }

    @Test
    public void testRating() {
        Rating rating = new Rating("Great", 5);
        JSONObject serialized = rating.toJson();
        assertEquals(5, serialized.getInt("score"));
        assertEquals("Great", serialized.getString("comment"));
    }

    @Test
    public void testFoodOutlet() {
        FoodOutlet outlet = new FoodOutlet("Chipotle", "UBC");
        JSONObject serialized = outlet.toJson();
        assertEquals("Chipotle", serialized.getString("name"));
        assertEquals("UBC", serialized.getString("location"));
    }
}
