package persistence;

import java.util.List;
import model.FoodOutlet;
import model.FoodOutletRatings;
import model.Rating;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class JsonWriterTest {
    private FoodOutletRatings ratings;

    @BeforeEach
    void runBefore() {
        ratings = new FoodOutletRatings();
        ratings.addRating(new FoodOutlet("Chipotle", "UBC"),
            new Rating("Great", 5));
        ratings.addRating(new FoodOutlet("Popeye's", "Wesbrook Mall"),
            new Rating("Bad", 1));
        FoodOutlet mcDonalds = new FoodOutlet("McDonald's", "University Boulevard");
        ratings.addRating(mcDonalds, new Rating("Good", 4));
        ratings.addRating(mcDonalds, new Rating("Meh", 3));
    }

    @Test
    void testWriterInvalidFile() {
        try {
            JsonWriter writer = new JsonWriter("src/data/my\0illegal:fileName.json");
            writer.write(ratings);
            fail("IOException was expected");
        } catch (Exception e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyRatings() {
        try {
            JsonWriter writer = new JsonWriter("src/data/reviewsEmptyWriteTest.json");
            writer.write(new FoodOutletRatings());

            JsonReader reader = new JsonReader("src/data/reviewsEmptyWriteTest.json");
            FoodOutletRatings result = reader.read();
            assertEquals(0, result.getFoodOutlets().size());
        } catch (Exception e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralRatings() {
        try {
            JsonWriter writer = new JsonWriter("src/data/reviewsWriteTest.json");
            writer.write(ratings);

            JsonReader reader = new JsonReader("src/data/reviewsWriteTest.json");
            FoodOutletRatings result = reader.read();
            assertEquals(result.getFoodOutlets(), ratings.getFoodOutlets());
            List<Rating> popeyesRatings = result.getRatings(new FoodOutlet("Popeye's",
                "Wesbrook Mall"));
            List<Rating> mcdonaldsRatings = result.getRatings(new FoodOutlet("McDonald's",
                "University Boulevard"));
            List<Rating> chipotleRatings = result.getRatings(new FoodOutlet("Chipotle",
                "UBC"));
            assertEquals(1, popeyesRatings.size());
            assertEquals(2, mcdonaldsRatings.size());
            assertEquals(1, chipotleRatings.size());

            assertEquals(1, popeyesRatings.get(0).getScore());
            assertEquals(4, mcdonaldsRatings.get(0).getScore());
            assertEquals(3, mcdonaldsRatings.get(1).getScore());
            assertEquals(5, chipotleRatings.get(0).getScore());
        } catch (Exception e) {
            fail("Exception should not have been thrown");
        }
    }

}
