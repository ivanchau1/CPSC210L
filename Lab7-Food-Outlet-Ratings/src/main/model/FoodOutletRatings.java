package model;

import model.exceptions.NoSuchOutletException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.HashSet;

import org.json.JSONArray;
import org.json.JSONObject;

// Represents a collection of food outlets with a list of
// ratings for each
public class FoodOutletRatings {

    private Map<FoodOutlet, List<Rating>> map;

    // EFFECTS: constructs an empty collection with no food outlets
    public FoodOutletRatings() {
        map = new HashMap<>();
    }

    // MODIFIES: this
    // EFFECTS: adds a rating for the given food outlet, if it has
    // not already been added for that outlet
    public void addRating(FoodOutlet outlet, Rating rating) {
        List<Rating> ratings = map.get(outlet);
        if (ratings == null) {
            ratings = new ArrayList<>();
            ratings.add(rating);
            map.put(outlet, ratings);
        } else {
            if (!ratings.contains(rating)) {
                ratings.add(rating);
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: removes a rating for the given food outlet;
    // does nothing if rating had not previously been added for the outlet
    public void removeRating(FoodOutlet outlet, Rating rating) {
        // HINT: What should happen when an outlet's last rating is removed?
        // How you decide to handle this edge case will impact the implementations
        // of several other methods, especially getFoodOutlets.

        // stub
        List<Rating> ratings = map.get(outlet);
        if (ratings == null) {
            return;
        } else if (ratings.contains(rating)) {
            ratings.remove(rating);
            if (ratings.isEmpty()) {
                map.remove(outlet);
            }
        }
    }

    // EFFECTS: returns list of ratings for food outlet in the order in which
    // they were added or null if there are no ratings for the outlet
    public List<Rating> getRatings(FoodOutlet outlet) {
        List<Rating> ratings = map.get(outlet);

        if (ratings == null) {
            return null;
        } else {
            return ratings;
        }
    }

    // EFFECTS: returns average score of all ratings for given food outlet;
    // throws NoSuchOutletException if there are no ratings for outlet
    public double getAverageScore(FoodOutlet outlet) throws NoSuchOutletException {
        if (map.get(outlet) == null) {
            throw new NoSuchOutletException();
        }

        double totalRating = 0;
        for (Rating r : map.get(outlet)) {
            totalRating += r.getScore();
        }

        return totalRating / getRatings(outlet).size(); // stub
    }

    // EFFECTS: returns set containing all food outlets that have ratings
    public Set<FoodOutlet> getFoodOutlets() {
        // HINT: For a map of type Map<K, V>, the keySet() method returns a Set<K>
        // containing all of the keys in the map.
        //
        // Depending on how you implemented removeRating, you may need to exclude
        // FoodOutlets with no ratings from the result. If so, make a fresh set
        // and insert into it only the elements from the original set that should
        // be included. This is for two reasons:
        // 1. Removing elements from the result of keySet() also removes them from the
        // original Map
        // 2. Removing elements from a Set while iterating over it in a for loop
        // is forbidden, and at best causes a ConcurrentModificationException

        Set<FoodOutlet> foodOutlets = map.keySet();

        return foodOutlets; // stub
    }

    // EFFECTS: returns a JSON array representation of this FoodOutletRatings
    public JSONArray toJson() {
        JSONArray jsonArray = new JSONArray();
        for (FoodOutlet foodOutlet : getFoodOutlets()) {
            JSONObject foodOutletRatingsJson = new JSONObject();
            foodOutletRatingsJson.put("food_outlet", foodOutlet.toJson());
            JSONArray ratingsJson = new JSONArray();
            for (Rating r : getRatings(foodOutlet)) {
                ratingsJson.put(r.toJson());
            }
            foodOutletRatingsJson.put("ratings", ratingsJson);
            jsonArray.put(foodOutletRatingsJson);
        }
        return jsonArray;
    }
}