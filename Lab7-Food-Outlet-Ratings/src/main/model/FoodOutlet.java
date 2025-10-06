package model;

import java.util.Objects;
import org.json.JSONObject;

// Represents a campus food outlet with a
// name and a location
public class FoodOutlet {
    private String name;
    private String location;

    // EFFECTS: constructs food outlet with given name and location
    public FoodOutlet(String name, String location) {
        this.name = name;
        this.location = location;
    }

    public String getName() {
        return name;
    }

    public String getLocation() {
        return location;
    }

    // EFFECTS: returns this food outlet ratings as a JSON object
    public JSONObject toJson() {
        JSONObject foodOutletJson = new JSONObject();
        foodOutletJson.put("name", getName());
        foodOutletJson.put("location", getLocation());
        return foodOutletJson;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        FoodOutlet that = (FoodOutlet) o;
        return Objects.equals(getName(), that.getName()) && Objects.equals(getLocation(), that.getLocation());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getLocation());
    }
}