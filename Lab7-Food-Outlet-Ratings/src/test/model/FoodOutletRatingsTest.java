package model;

import model.exceptions.NoSuchOutletException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class FoodOutletRatingsTest {

    private FoodOutletRatings foodOutletRatings;
    private Rating r1;
    private Rating r2;
    private FoodOutlet o1;
    private FoodOutlet o2;

    @BeforeEach
    void setUp() {
        foodOutletRatings = new FoodOutletRatings();
        r1 = new Rating("Very good", 5);
        r2 = new Rating("Very bad", 1);
        o1 = new FoodOutlet("Pasta", "Computer Science");
        o2 = new FoodOutlet("Pho", "Sauder");
    }

    @Test
    void testConstructor() {
        assertTrue(foodOutletRatings.getFoodOutlets().isEmpty());
    }

    @Test
    void testGetNonExistentOutletRatings() {
        assertNull(foodOutletRatings.getRatings(o1));
        assertNull(foodOutletRatings.getRatings(o2));
    }

    @Test
    void testGetNonExistentOutletAverageScore() {
        try {
            foodOutletRatings.getAverageScore(o1);
            fail("Should have thrown exception");
        } catch (NoSuchOutletException e) {
            // pass
        }
    }

    @Test
    void testAddOneRating() {
        try {
            assertNull(foodOutletRatings.getRatings(o1));
            assertTrue(foodOutletRatings.getFoodOutlets().isEmpty());

            foodOutletRatings.addRating(o1, r1);

            assertEquals(1, foodOutletRatings.getFoodOutlets().size());
            assertTrue(foodOutletRatings.getFoodOutlets().contains(o1));
            assertEquals(1, foodOutletRatings.getRatings(o1).size());
            assertTrue(foodOutletRatings.getRatings(o1).contains(r1));
            assertEquals(5, foodOutletRatings.getAverageScore(o1));
        } catch (NoSuchOutletException e) {
            fail("Should not have thrown exception");
        }
    }

    @Test
    void testAddOneRatingToNonExistentOutlet() {
        foodOutletRatings.addRating(o1, r1);

        assertTrue(foodOutletRatings.getRatings(o1).contains(r1));
        assertEquals(1, foodOutletRatings.getFoodOutlets().size());
    }

    @Test
    void testAddTwoDifferentRatingsSameFoodOutlet() {
        try {
            foodOutletRatings.addRating(o1, r1);

            assertTrue(foodOutletRatings.getRatings(o1).contains(r1));
            assertEquals(1, foodOutletRatings.getFoodOutlets().size());
            assertTrue(foodOutletRatings.getFoodOutlets().contains(o1));

            foodOutletRatings.addRating(o1, r2);

            assertEquals(1, foodOutletRatings.getFoodOutlets().size());
            assertTrue(foodOutletRatings.getFoodOutlets().contains(o1));
            assertEquals(2, foodOutletRatings.getRatings(o1).size());
            assertTrue(foodOutletRatings.getRatings(o1).contains(r1));
            assertTrue(foodOutletRatings.getRatings(o1).contains(r2));
            assertEquals(3, foodOutletRatings.getAverageScore(o1));
        } catch (NoSuchOutletException e) {
            fail("Should not have thrown exception");
        }
    }

    @Test
    void testAddTwoDifferentRatingsDifferentFoodOutlet() {
        try {
            foodOutletRatings.addRating(o1, r1);
            foodOutletRatings.addRating(o2, r2);

            assertEquals(2, foodOutletRatings.getFoodOutlets().size());
            assertTrue(foodOutletRatings.getFoodOutlets().contains(o1));
            assertTrue(foodOutletRatings.getFoodOutlets().contains(o2));
            assertEquals(1, foodOutletRatings.getRatings(o1).size());
            assertEquals(1, foodOutletRatings.getRatings(o2).size());
            assertTrue(foodOutletRatings.getRatings(o1).contains(r1));
            assertTrue(foodOutletRatings.getRatings(o2).contains(r2));
            assertEquals(5, foodOutletRatings.getAverageScore(o1));
            assertEquals(1, foodOutletRatings.getAverageScore(o2));
        } catch (NoSuchOutletException e) {
            fail("Should not have thrown exception");
        }
    }

    @Test
    void testAddTwoDuplicateRatingsSameFoodOutlet() {
        foodOutletRatings.addRating(o1, r1);

        assertEquals(1, foodOutletRatings.getFoodOutlets().size());
        assertTrue(foodOutletRatings.getFoodOutlets().contains(o1));
        assertEquals(1, foodOutletRatings.getRatings(o1).size());
        assertTrue(foodOutletRatings.getRatings(o1).contains(r1));

        foodOutletRatings.addRating(o1, r1);

        assertEquals(1, foodOutletRatings.getFoodOutlets().size());
        assertTrue(foodOutletRatings.getFoodOutlets().contains(o1));
        assertEquals(1, foodOutletRatings.getRatings(o1).size());
        assertTrue(foodOutletRatings.getRatings(o1).contains(r1));
    }

    @Test
    void testAddTwoDuplicateRatingsDifferentFoodOutlet() {
        try {
            foodOutletRatings.addRating(o1, r1);
            foodOutletRatings.addRating(o2, r1);

            assertEquals(2, foodOutletRatings.getFoodOutlets().size());
            assertTrue(foodOutletRatings.getFoodOutlets().contains(o1));
            assertTrue(foodOutletRatings.getFoodOutlets().contains(o2));
            assertEquals(1, foodOutletRatings.getRatings(o1).size());
            assertEquals(1, foodOutletRatings.getRatings(o2).size());
            assertTrue(foodOutletRatings.getRatings(o1).contains(r1));
            assertTrue(foodOutletRatings.getRatings(o2).contains(r1));
            assertEquals(5, foodOutletRatings.getAverageScore(o1));
            assertEquals(5, foodOutletRatings.getAverageScore(o2));
        } catch (NoSuchOutletException e) {
            fail("Should not have thrown exception");
        }
    }

    @Test
    void testAverageScoreException() {
        try {
            foodOutletRatings.addRating(o1, r1);
            foodOutletRatings.removeRating(o1, r1);

            assertEquals(0, foodOutletRatings.getFoodOutlets().size());
            assertNull(foodOutletRatings.getRatings(o1));
            assertEquals(0, foodOutletRatings.getAverageScore(o1));
            fail("Should have thrown exception");
        } catch (NoSuchOutletException e) {
            // pass
        }
    }

    @Test
    void testAverageScoreNoOutlet() {
        try {
            foodOutletRatings.getAverageScore(o1);
            fail("Should have thrown exception");
        } catch (NoSuchOutletException e) {
            // pass
        }
    }

    @Test
    void testRemoveNonExistentRating() {
        try {
            foodOutletRatings.addRating(o1, r1);

            assertEquals(1, foodOutletRatings.getFoodOutlets().size());
            assertTrue(foodOutletRatings.getFoodOutlets().contains(o1));
            assertEquals(1, foodOutletRatings.getRatings(o1).size());
            assertTrue(foodOutletRatings.getRatings(o1).contains(r1));
            assertEquals(5, foodOutletRatings.getAverageScore(o1));

            foodOutletRatings.removeRating(o1, r2);

            assertEquals(1, foodOutletRatings.getFoodOutlets().size());
            assertTrue(foodOutletRatings.getFoodOutlets().contains(o1));
            assertEquals(1, foodOutletRatings.getRatings(o1).size());
            assertTrue(foodOutletRatings.getRatings(o1).contains(r1));
            assertEquals(5, foodOutletRatings.getAverageScore(o1));
        } catch (NoSuchOutletException e) {
            fail("Should not have thrown exception");
        }
    }

    @Test
    void testRemoveRatingFromExistingOutlet() {
        foodOutletRatings.addRating(o1, r1);
        assertEquals(1, foodOutletRatings.getRatings(o1).size());
        assertTrue(foodOutletRatings.getRatings(o1).contains(r1));
        List<Rating> ratings = new ArrayList<>();
        ratings.add(r1);
        assertEquals(ratings, foodOutletRatings.getRatings(o1));

        foodOutletRatings.removeRating(o1, r1);
        assertNull(foodOutletRatings.getRatings(o1));
        assertEquals(0, foodOutletRatings.getFoodOutlets().size());
    }

    @Test
    void testRemoveRatingFromNonExistentOutlet() {
        foodOutletRatings.addRating(o1, r1);
        foodOutletRatings.removeRating(o2, r1);

        assertNull(foodOutletRatings.getRatings(o2));
        assertEquals(1, foodOutletRatings.getRatings(o1).size());
        assertTrue(foodOutletRatings.getRatings(o1).contains(r1));
    }

    @Test
    void testRemoveNonExistentRatingAndFoodOutlet() {
        try {
            foodOutletRatings.removeRating(o1, r2);

            assertTrue(foodOutletRatings.getFoodOutlets().isEmpty());
            assertFalse(foodOutletRatings.getFoodOutlets().contains(o1));
            assertFalse(foodOutletRatings.getFoodOutlets().contains(o2));
            assertNull(foodOutletRatings.getRatings(o1));
            assertNull(foodOutletRatings.getRatings(o2));
            foodOutletRatings.getAverageScore(o2);
            fail("Should have thrown exception");
        } catch (NoSuchOutletException e) {
            // pass
        }
    }

    @Test
    void testRemoveRating() {
        try {
            foodOutletRatings.addRating(o1, r1);
            foodOutletRatings.addRating(o1, r2);

            assertEquals(1, foodOutletRatings.getFoodOutlets().size());
            assertTrue(foodOutletRatings.getFoodOutlets().contains(o1));
            assertEquals(2, foodOutletRatings.getRatings(o1).size());
            assertTrue(foodOutletRatings.getRatings(o1).contains(r1));
            assertTrue(foodOutletRatings.getRatings(o1).contains(r2));
            assertEquals(3, foodOutletRatings.getAverageScore(o1));

            foodOutletRatings.removeRating(o1, r1);

            assertEquals(1, foodOutletRatings.getFoodOutlets().size());
            assertTrue(foodOutletRatings.getFoodOutlets().contains(o1));
            assertEquals(1, foodOutletRatings.getRatings(o1).size());
            assertFalse(foodOutletRatings.getRatings(o1).contains(r1));
            assertTrue(foodOutletRatings.getRatings(o1).contains(r2));
            assertEquals(1, foodOutletRatings.getAverageScore(o1));
        } catch (NoSuchOutletException e) {
            fail("Should not have thrown exception");
        }
    }

    @Test
    void testRemoveLastRating() {
        try {
            foodOutletRatings.addRating(o1, r1);

            assertEquals(1, foodOutletRatings.getFoodOutlets().size());
            assertTrue(foodOutletRatings.getFoodOutlets().contains(o1));
            assertEquals(1, foodOutletRatings.getRatings(o1).size());
            assertTrue(foodOutletRatings.getRatings(o1).contains(r1));
            assertEquals(5, foodOutletRatings.getAverageScore(o1));

            foodOutletRatings.removeRating(o1, r1);

            assertEquals(0, foodOutletRatings.getFoodOutlets().size());
            assertFalse(foodOutletRatings.getFoodOutlets().contains(o1));
            assertNull(foodOutletRatings.getRatings(o1));
            assertEquals(0, foodOutletRatings.getAverageScore(o1));
            fail("Should have thrown exception");
        } catch (NoSuchOutletException e) {
            // pass
        }
    }

    @Test
    void testAddMultipleRatingsRemoveAll() {
        foodOutletRatings.addRating(o1, r1);

        List<Rating> ratings = new ArrayList<>();
        ratings.add(r1);
        assertEquals(ratings, foodOutletRatings.getRatings(o1));
        assertEquals(1, foodOutletRatings.getRatings(o1).size());
        assertEquals(1, foodOutletRatings.getFoodOutlets().size());

        foodOutletRatings.addRating(o1, r2);

        ratings.add(r2);
        assertEquals(1, foodOutletRatings.getFoodOutlets().size());
        assertTrue(foodOutletRatings.getFoodOutlets().contains(o1));
        assertEquals(2, foodOutletRatings.getRatings(o1).size());
        assertTrue(foodOutletRatings.getRatings(o1).contains(r1));
        assertTrue(foodOutletRatings.getRatings(o1).contains(r2));
        assertEquals(ratings, foodOutletRatings.getRatings(o1));

        foodOutletRatings.removeRating(o1, r1);

        ratings.remove(r1);

        assertEquals(1, foodOutletRatings.getFoodOutlets().size());
        assertTrue(foodOutletRatings.getFoodOutlets().contains(o1));
        assertEquals(1, foodOutletRatings.getRatings(o1).size());
        assertFalse(foodOutletRatings.getRatings(o1).contains(r1));
        assertTrue(foodOutletRatings.getRatings(o1).contains(r2));
        assertEquals(ratings, foodOutletRatings.getRatings(o1));

        foodOutletRatings.removeRating(o1, r2);

        assertEquals(0, foodOutletRatings.getFoodOutlets().size());
        assertFalse(foodOutletRatings.getFoodOutlets().contains(o1));
        assertNull(foodOutletRatings.getRatings(o1));
    }

    @Test
    void testAddAndRemoveAllFoodOutlets() {
        foodOutletRatings.addRating(o1, r1);
        foodOutletRatings.addRating(o2, r2);

        List<Rating> ratings1 = new ArrayList<>();
        ratings1.add(r1);
        assertEquals(ratings1, foodOutletRatings.getRatings(o1));

        List<Rating> ratings2 = new ArrayList<>();
        ratings2.add(r2);
        assertEquals(ratings2, foodOutletRatings.getRatings(o2));

        assertEquals(2, foodOutletRatings.getFoodOutlets().size());

        foodOutletRatings.removeRating(o1, r1);
        foodOutletRatings.removeRating(o2, r2);

        assertNull(foodOutletRatings.getRatings(o1));
        assertNull(foodOutletRatings.getRatings(o2));
        assertTrue(foodOutletRatings.getFoodOutlets().isEmpty());
    }

    @Test
    void testGetNonExistentRatings() {
        assertNull(foodOutletRatings.getRatings(o1));
        assertNull(foodOutletRatings.getRatings(o2));
    }

    @Test
    void testGetExistingRating() {
        foodOutletRatings.addRating(o1, r1);

        assertEquals(1, foodOutletRatings.getRatings(o1).size());
        assertTrue(foodOutletRatings.getRatings(o1).contains(r1));
    }

    @Test
    void testGetAverageScoreDecimal() {
        try {
            foodOutletRatings.addRating(o1, new Rating("Good", 4));
            foodOutletRatings.addRating(o1, new Rating("Good", 3));
            foodOutletRatings.addRating(o1, new Rating("Bad", 1));
            foodOutletRatings.addRating(o1, new Rating("Bad", 2));

            assertEquals(2.5, foodOutletRatings.getAverageScore(o1));
        } catch (NoSuchOutletException e) {
            fail("Should not have thrown exception");
        }
    }

    @Test
    void testGetAverageScore() {
        foodOutletRatings.addRating(o1, r1);
        List<Rating> ratings = new ArrayList<>();
        ratings.add(r1);
        try {
            assertEquals(5, foodOutletRatings.getAverageScore(o1));
        } catch (NoSuchOutletException e) {
            fail("Should not have thrown exception");
        }

        r2 = new Rating("Good", 2);
        Rating r3 = new Rating("Ok", 2);
        Rating r4 = new Rating("Bad", 1);
        Rating r5 = new Rating("Good", 5);

        foodOutletRatings.addRating(o1, r2);
        foodOutletRatings.addRating(o1, r3);
        foodOutletRatings.addRating(o1, r4);
        foodOutletRatings.addRating(o1, r5);

        ratings.add(r2);
        ratings.add(r3);
        ratings.add(r4);
        ratings.add(r5);

        try {
            assertEquals(ratings, foodOutletRatings.getRatings(o1));
            assertEquals(3, foodOutletRatings.getAverageScore(o1));
        } catch (NoSuchOutletException e) {
            fail("Should not have thrown exception");
        }
    }
}