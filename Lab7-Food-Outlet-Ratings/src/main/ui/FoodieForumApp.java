package ui;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import model.FoodOutlet;
import model.FoodOutletRatings;
import model.Rating;
import model.exceptions.NoSuchOutletException;
import persistence.JsonReader;
import persistence.JsonWriter;

// Console-based UI for interacting with FoodOutletRatings
public class FoodieForumApp {
    private static final String JSON_STORE = "src/data/reviews.json";
    private Scanner scanner;
    private FoodOutletRatings foodOutletRatings;

    // EFFECTS: runs the FoodieForum application
    public FoodieForumApp() {
        scanner = new Scanner(System.in);
        foodOutletRatings = new FoodOutletRatings();
        System.out.println("----------------------      Welcome to      ----------------------");
        printLogo();
        printDivider();
        runMainMenu();
    }

    private void runMainMenu() {
        boolean isRunning = true;
        String command;

        while (isRunning) {
            displayMainMenu();
            command = scanner.nextLine().toLowerCase();

            if (command.equals("q")) {
                isRunning = false;
            } else {
                printDivider();
                processMainMenuInput(command);
            }
        }

        System.out.println("-------------------      Thanks for using      -------------------");
        printLogo();
        printDivider();
    }

    private void displayMainMenu() {
        System.out.println("Select an option:\n");
        System.out.println("a: Add a new food outlet rating");
        System.out.println("r: Remove a food outlet rating");
        System.out.println("v: View food outlet ratings");
        System.out.println("vs: View statistics for food outlet ratings");
        System.out.println("l: Load food outlet ratings from file");
        System.out.println("s: Save food outlet ratings to file");
        System.out.println("q: Quit the application");
        printDivider();
    }

    private void processMainMenuInput(String command) {
        switch (command) {
            case "a":
                addFoodOutletRating();
                break;
            case "r":
                removeFoodOutletRating();
                break;
            case "v":
                viewFoodOutletRatings();
                break;
            case "vs":
                viewFoodOutletRatingStatistics();
                break;
            default:
                processMainMenuPersistenceInput(command);
        }
    }

    private void processMainMenuPersistenceInput(String command) {
        switch (command) {
            case "l":
                loadFoodOutletRatings();
                break;
            case "s":
                saveFoodOutletRatings();
                break;
            default:
                System.out.println("Invalid option inputted. Please try again.");
        }
    }

    private void addFoodOutletRating() {
        System.out.println("Enter the food outlet's name:");
        String foodOutletName = scanner.nextLine();
        System.out.println("\nEnter the food outlet's location:");
        String foodOutletLocation = scanner.nextLine();

        System.out.println("\nYour score (out of 5):");
        int ratingScore = scanner.nextInt();

        System.out.println("\nYour comments:");
        scanner.nextLine();
        String ratingComment = scanner.nextLine();

        foodOutletRatings.addRating(new FoodOutlet(foodOutletName, foodOutletLocation),
            new Rating(ratingComment, ratingScore));
        printDivider();
    }

    private void removeFoodOutletRating() {
        System.out.println("Enter the food outlet's name:");
        String foodOutletName = scanner.nextLine();
        System.out.println("\nEnter the food outlet's location:");
        String foodOutletLocation = scanner.nextLine();

        FoodOutlet targetFoodOutlet = new FoodOutlet(foodOutletName, foodOutletLocation);
        if (foodOutletRatings.getRatings(targetFoodOutlet) == null) {
            System.out.println("Error: No ratings found for food outlet named \"" + foodOutletName
                    + "\" at location \"" + foodOutletLocation + "\".");
        } else {
            printRatings(targetFoodOutlet);

            System.out.println("\nEnter the index of the rating that you'd like to remove:");
            int index = scanner.nextInt();
            scanner.nextLine();
            index--;

            Rating ratingToRemove = foodOutletRatings.getRatings(targetFoodOutlet).get(index);
            foodOutletRatings.removeRating(targetFoodOutlet, ratingToRemove);
        }
        printDivider();
    }

    private void viewFoodOutletRatings() {
        displayViewMenu();
        String input = scanner.nextLine();
        processViewMenuInputs(input);
    }

    private void displayViewMenu() {
        System.out.println("What ratings would you like to view?\n");
        System.out.println("a: All food outlet ratings");
        System.out.println("s: Ratings for a selected food outlet");
        System.out.println("b: Ratings for the food outlet with the highest average score");
        System.out.println("q: Go back to the main menu");
        printDivider();
    }

    private void processViewMenuInputs(String input) {
        printDivider();
        switch (input) {
            case "a":
                printAllFoodOutletRatings();
                break;
            case "s":
                printSelectedFoodOutletRatings();
                break;
            case "b":
                printBestFoodOutletRatings();
                break;
            case "q":
                break;
            default:
                System.out.println("Invalid option inputted. Please try again.");
        }
    }

    private void printAllFoodOutletRatings() {
        for (FoodOutlet foodOutlet : foodOutletRatings.getFoodOutlets()) {
            printFoodOutletRatings(foodOutlet);
        }
    }

    private void printSelectedFoodOutletRatings() {
        FoodOutlet foodOutletToFind = findSelectedFoodOutlet();
        if (foodOutletToFind != null) {
            printFoodOutletRatings(foodOutletToFind);
        }
    }

    private FoodOutlet findSelectedFoodOutlet() {
        System.out.println("Enter the name of the food outlet:");
        String foodOutletName = scanner.nextLine();
        System.out.println("\nEnter the location of the food outlet:");
        String foLocation = scanner.nextLine();
        FoodOutlet foodOutletToFind = new FoodOutlet(foodOutletName, foLocation);
        printDivider();
        if (foodOutletRatings.getFoodOutlets().contains(foodOutletToFind)) {
            return foodOutletToFind;
        } else {
            System.out.println("Error: No food outlet named \"" + foodOutletName
                    + "\" at location \"" + foLocation + "\".");
            printDivider();
            return null;
        }
    }

    private void printFoodOutletRatings(FoodOutlet foodOutlet) {
        System.out.println("Reviews for " + foodOutlet.getName() + " @ " + foodOutlet.getLocation());
        if (foodOutletRatings.getRatings(foodOutlet) == null) {
            System.out.println("No reviews found.");
        } else {
            printRatings(foodOutlet);
        }
        printDivider();
    }

    private void printRatings(FoodOutlet fo) {
        List<Rating> ratings = foodOutletRatings.getRatings(fo);
        for (int i = 0; i < ratings.size(); i++) {
            Rating r = ratings.get(i);
            printDivider();
            System.out.println("Review #" + (i + 1) + "\n---");
            System.out.println("Score: " + r.getScore() + "/5");
            System.out.println("Comments: " + r.getComment());
        }
    }

    private void printBestFoodOutletRatings() {
        FoodOutlet bestFoodOutlet = findBestFoodOutlet();
        System.out.println("Best Food Outlet: " + bestFoodOutlet.getName() + " @ " + bestFoodOutlet.getLocation());
        try {
            System.out.println("Score: " + foodOutletRatings.getAverageScore(bestFoodOutlet));
        } catch (NoSuchOutletException e) {
            System.out.println("Error: No such food outlet found.");
        }

        printRatings(bestFoodOutlet);
        printDivider();
    }

    private FoodOutlet findBestFoodOutlet() {
        Set<FoodOutlet> foodOutlets = foodOutletRatings.getFoodOutlets();
        FoodOutlet bestFoodOutlet = null;
        double bestScore = 0.0;

        for (FoodOutlet foodOutlet : foodOutlets) {
            try {
                double currentScore = foodOutletRatings.getAverageScore(foodOutlet);
                if (currentScore > bestScore) {
                    bestFoodOutlet = foodOutlet;
                    bestScore = currentScore;
                }
            } catch (NoSuchOutletException e) {
                // skip food outlets with no ratings
            }
        }

        return bestFoodOutlet;
    }

    private FoodOutlet findWorstFoodOutlet() {
        Set<FoodOutlet> foodOutlets = foodOutletRatings.getFoodOutlets();
        FoodOutlet worstFoodOutlet = null;
        double worstScore = 5.1; // impossibly high score

        for (FoodOutlet foodOutlet : foodOutlets) {
            try {
                double currentScore = foodOutletRatings.getAverageScore(foodOutlet);
                if (currentScore < worstScore) {
                    worstFoodOutlet = foodOutlet;
                    worstScore = currentScore;
                }
            } catch (NoSuchOutletException e) {
                // skip food outlets with no ratings
            }
        }

        return worstFoodOutlet;
    }

    private void viewFoodOutletRatingStatistics() {
        displayViewStatisticsMenu();
        String input = scanner.nextLine();
        printDivider();
        processViewStatisticsMenuInputs(input);
    }

    private void displayViewStatisticsMenu() {
        System.out.println("What statistics would you like to view?\n");
        System.out.println("a: All food outlet rating statistics");
        System.out.println("s: Statistics for a selected food outlet");
        System.out.println("b: Food outlet with highest average score");
        System.out.println("w: Food outlet with lowest average score");
        System.out.println("q: Go back to the main menu");
        printDivider();
    }

    private void processViewStatisticsMenuInputs(String input) {
        switch (input) {
            case "a":
                viewAllFoodOutletRatingStatistics();
                break;
            case "s":
                viewSelectedFoodOutletRatingStatistics();
                break;
            case "b":
                viewBestFoodOutletRatingStatistics();
                break;
            case "w":
                viewWorstFoodOutletRatingStatistics();
                break;
            case "q":
                break;
            default:
                System.out.println("Invalid option inputted. Please try again.");
        }
    }

    private void viewAllFoodOutletRatingStatistics() {
        Set<FoodOutlet> foodOutlets = foodOutletRatings.getFoodOutlets();

        if (foodOutlets.isEmpty()) {
            System.out.println("Error: No food outlets found.");
            printDivider();
        } else {
            for (FoodOutlet foodOutlet : foodOutletRatings.getFoodOutlets()) {
                printRatingStatistics(foodOutlet);
            }
        }
    }

    private void printRatingStatistics(FoodOutlet foodOutlet) {
        String foodOutletName = foodOutlet.getName();
        String foodOutletLocation = foodOutlet.getLocation();

        try {
            double score = foodOutletRatings.getAverageScore(foodOutlet);
            List<Rating> ratings = foodOutletRatings.getRatings(foodOutlet);
            int numReviews = (ratings != null) ? ratings.size() : 0;

            System.out.println("Statistics for " + foodOutletName + " @ " + foodOutletLocation);
            System.out.println("\nAverage Score: " + score);
            System.out.println("Number of Reviews: " + numReviews);
            printDivider();
        } catch (NoSuchOutletException e) {
            System.out.println("No statistics found for " + foodOutletName + " @ "
                    + foodOutletLocation + ".");
        }
    }

    private void viewSelectedFoodOutletRatingStatistics() {
        FoodOutlet foodOutletToFind = findSelectedFoodOutlet();
        if (foodOutletToFind != null) {
            printRatingStatistics(foodOutletToFind);
        }
    }

    private void viewBestFoodOutletRatingStatistics() {
        FoodOutlet foodOutletToFind = findBestFoodOutlet();
        if (foodOutletToFind != null) {
            printRatingStatistics(foodOutletToFind);
        }
    }

    private void viewWorstFoodOutletRatingStatistics() {
        FoodOutlet foodOutletToFind = findWorstFoodOutlet();
        if (foodOutletToFind != null) {
            printRatingStatistics(foodOutletToFind);
        }
    }

    private void loadFoodOutletRatings() {
        JsonReader reader = new JsonReader(JSON_STORE);
        try {
            foodOutletRatings = reader.read();
        } catch (IOException e) {
            System.out.println("Error: Could not read file.");
        }
    }

    private void saveFoodOutletRatings() {
        JsonWriter writer = new JsonWriter(JSON_STORE);
        try {
            writer.write(foodOutletRatings);
        } catch (FileNotFoundException e) {
            System.out.println("Error: Could not save file.");
        }
    }

    private void printDivider() {
        System.out.println("------------------------------------------------------------------");
    }

    private void printLogo() {
        System.out.println("    ______                ___      ______                         \n"
                + "   / ____/___  ____  ____/ (_)__  / ____/___  _______  ______ ___ \n"
                + "  / /_  / __ \\/ __ \\/ __  / / _ \\/ /_  / __ \\/ ___/ / / / __ `__ \\\n"
                + " / __/ / /_/ / /_/ / /_/ / /  __/ __/ / /_/ / /  / /_/ / / / / / /\n"
                + "/_/    \\____/\\____/\\__,_/_/\\___/_/    \\____/_/   \\__,_/_/ /_/ /_/ \n"
                + "                                                                  ");
    }
}
