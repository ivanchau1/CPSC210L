package ui;

import model.Flashcard;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// A flashcard application that allows the user to add and review their flashcards
public class FlashcardReviewer {
    private List<Flashcard> flashcards;
    private int currentCardIndex = 0;

    private Scanner scanner;
    private boolean isProgramRunning;

    // EFFECTS: creates an instance of the FlashcardReviewer console ui application
    public FlashcardReviewer() {
        init();

        printDivider();
        System.out.println("Welcome to the Flashcard Reviewer app!");
        printDivider();

        while (this.isProgramRunning) {
            handleMenu();
        }
    }

    // MODIFIES: this
    // EFFECTS: initializes the application with the starting values
    public void init() {
        this.flashcards = new ArrayList<>();
        this.scanner = new Scanner(System.in);
        this.isProgramRunning = true;
    }

    // EFFECTS: displays and processes inputs for the main menu
    public void handleMenu() {
        displayMenu();
        String input = this.scanner.nextLine();
        processMenuCommands(input);
    }

    // EFFECTS: displays a list of commands that can be used in the main menu
    public void displayMenu() {
        System.out.println("Please select an option:\n");
        System.out.println("a: Add a new flashcard");
        System.out.println("v: View all flashcards");
        System.out.println("r: View flashcards that aren't marked as reviewed");
        System.out.println("q: Exit the application");
        printDivider();
    }

    // EFFECTS: processes the user's input in the main menu
    public void processMenuCommands(String input) {
        printDivider();
        switch (input) {
            case "a":
                addNewFlashcard();
                break;
            case "v":
                viewFlashcards();
                break;
            case "r":
                reviewFlashcards();
                break;
            case "q":
                quitApplication();
                break;
            default:
                System.out.println("Invalid option inputted. Please try again.");
        }
        printDivider();
    }

    // MODIFIES: this
    // EFFECTS: adds a flashcard to the list of flashcards
    public void addNewFlashcard() {
        System.out.println("Please enter the flashcard's prompt:");
        String prompt = this.scanner.nextLine();

        System.out.println("\nPlease enter the flashcard's answer:");
        String answer = this.scanner.nextLine();

        // TODO: complete the below line of code to create a new flashcard
        Flashcard flashcard = new Flashcard(prompt, answer);

        this.flashcards.add(flashcard);
        System.out.println("\nNew flashcard successfully created!");
    }

    // MODIFIES: this
    // EFFECTS: displays all flashcards one at a time
    public void viewFlashcards() {
        displayGivenFlashcards(this.flashcards);
    }

    // MODIFIES: this
    // EFFECTS: displays only the flashcards that aren't marked as reviewed, one at a time
    public void reviewFlashcards() {
        List<Flashcard> flashcardsToReview = new ArrayList<>();

        for (Flashcard currentFlashcard : this.flashcards) {
            // TODO: fix the below if-statement so it correctly selects a flashcard that hasn't been marked as reviewed
            if (!currentFlashcard.getReviewStatus()) {
                flashcardsToReview.add(currentFlashcard);
            }
        }

        displayGivenFlashcards(flashcardsToReview);
    }

    // MODIFIES: this
    // EFFECTS: displays the given list of flashcards and handles inputs related to viewing the flashcards
    public void displayGivenFlashcards(List<Flashcard> flashcards) {
        if (flashcards.isEmpty()) {
            System.out.println("Error: No flashcards to review. Try adding a flashcard first!");
            return;
        }

        displayViewMenu();
        String input = "";
        while (!input.equals("q")) {
            Flashcard currentFlashcard = flashcards.get(this.currentCardIndex);
            displayFlashcard(currentFlashcard);
            input = this.scanner.nextLine();
            handleViewCommands(input, flashcards);
        }
        this.currentCardIndex = 0;
    }

    // EFFECTS: displays a list of commands that can be used in the view flashcards menu
    public void displayViewMenu() {
        System.out.println("Enter 'a' to display the flashcard's answer.");
        System.out.println("Enter 'r' to mark the flashcard as reviewed.");
        System.out.println("Enter 'u' to mark the flashcard as not reviewed.");
        System.out.println("Enter 'n' to move to the next flashcard.");
        System.out.println("Enter 'p' to move to the previous flashcard.");
        System.out.println("Enter 'q' to return to the menu.");
    }

    // EFFECTS: displays the given flashcard
    public void displayFlashcard(Flashcard card) {
        printDivider();
        System.out.println("Flashcard #" + (this.currentCardIndex + 1));
        System.out.println(card.getPrompt());
    }

    // MODIFIES: this
    // EFFECTS: processes the user's input in the view flashcards menu
    public void handleViewCommands(String input, List<Flashcard> flashcards) {
        System.out.print("\n");

        Flashcard currentFlashcard = flashcards.get(this.currentCardIndex);
        switch (input) {
            case "a":
                displayFlashcardAnswer(currentFlashcard);
                break;
            case "r":
                markFlashcardAsReviewed(currentFlashcard);
                break;
            case "u":
                markFlashcardAsNotReviewed(currentFlashcard);
                break;
            case "n":
                getNextFlashcard(flashcards);
                break;
            case "p":
                getPreviousFlashcard();
                break;
            case "q":
                System.out.println("Returning to the menu...");
                break;
            default:
                System.out.println("Invalid option inputted. Please try again.");
        }
    }

    // EFFECTS: displays the answer of the given flashcard
    public void displayFlashcardAnswer(Flashcard flashcard) {
        System.out.println("Answer: " + flashcard.getAnswer());
    }

    // MODIFIES: flashcard
    // EFFECTS: marks the given flashcard as reviewed
    public void markFlashcardAsReviewed(Flashcard flashcard) {
        // TODO: write the necessary code below to mark the given flashcard as reviewed
        flashcard.markAsReviewed();

        System.out.println("Flashcard " + (this.currentCardIndex + 1) + " marked as reviewed!");
    }

    // MODIFIES: flashcard
    // EFFECTS: marks the given flashcard as not reviewed
    public void markFlashcardAsNotReviewed(Flashcard flashcard) {
        // TODO: write the necessary code below to mark the given flashcard as not reviewed
        flashcard.markAsNotReviewed();

        System.out.println("Flashcard " + (this.currentCardIndex + 1) + " marked as not reviewed!");
    }

    // MODIFIES: this
    // EFFECTS: if there is another flashcard to display, increments the current flashcard index
    public void getNextFlashcard(List<Flashcard> flashcards) {
        if (this.currentCardIndex >= flashcards.size() - 1) {
            System.out.println("Error: No more new flashcards to display!");
        } else {
            this.currentCardIndex++;
        }
    }

    // MODIFIES: this
    // EFFECTS: if there is a previous flashcard to display, decrements the current flashcard index
    public void getPreviousFlashcard() {
        if (this.currentCardIndex <= 0) {
            System.out.println("Error: No more previous flashcards to display!");
        } else {
            this.currentCardIndex--;
        }
    }

    // MODIFIES: this
    // EFFECTS: prints a closing message and marks the program as not running
    public void quitApplication() {
        System.out.println("Thanks for using the Flashcard Reviewer app!");
        System.out.println("Have a good day!");
        this.isProgramRunning = false;
    }

    // EFFECTS: prints out a line of dashes to act as a divider
    private void printDivider() {
        System.out.println("------------------------------------");
    }
}
