package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class FlashcardTest {
    // TODO: remember to declare your flashcards here!
    private Flashcard flashCardOne;
    private Flashcard flashCardTwo;
    
    @BeforeEach
    void runBefore() {
        // TODO: add examples of at least two flashcards!
        flashCardOne = new Flashcard("What course is this?", "CPSC 210");
        flashCardTwo = new Flashcard("What is the prerequiste for CPSC 210?", "CPSC 110");


    }

    @Test
    void testConstructor() {
        // TODO: write tests for the constructor below!
        assertEquals("What course is this?", flashCardOne.getPrompt());
        assertEquals("What is the prerequiste for CPSC 210?", flashCardTwo.getPrompt());
        assertEquals("CPSC 210", flashCardOne.getAnswer());
        assertEquals("CPSC 110", flashCardTwo.getAnswer());
    }

    @Test
    void testCheckAnswer() {
        // TODO: write tests for the checkAnswer method below!
        assertTrue(flashCardTwo.checkAnswer("CPSC 110"));
        assertFalse(flashCardTwo.checkAnswer("cpsc 110"));
    }

    @Test
    void testMarkAsReviewed() {
        // TODO: write tests for the markAsReviewed method below!
        assertFalse(flashCardOne.getReviewStatus());
        flashCardOne.markAsReviewed();
        assertTrue(flashCardOne.getReviewStatus());
        flashCardOne.markAsNotReviewed();
        assertFalse(flashCardOne.getReviewStatus());

        flashCardOne.markAsNotReviewed();
        assertFalse(flashCardOne.getReviewStatus());

        flashCardTwo.markAsReviewed();
        assertTrue(flashCardTwo.getReviewStatus());
        flashCardTwo.markAsNotReviewed();
        assertFalse(flashCardTwo.getReviewStatus());
        flashCardTwo.markAsReviewed();
        assertTrue(flashCardTwo.getReviewStatus());

        flashCardTwo.markAsReviewed();
        assertTrue(flashCardTwo.getReviewStatus());

    }

    @Test
    void testMarkAsNotReviewed() {
        // TODO: write tests for the markAsNotReviewed method below!
        flashCardOne.markAsNotReviewed();
        assertFalse(flashCardOne.getReviewStatus());
    }


    /**
     DO NOT EDIT THE BELOW TESTS!
     You can assume they are completely correct.
     Feel free to refer to them if needed.
    **/

    @Test
    void testSetters() {
        Flashcard flashcard = new Flashcard("What is the world's tallest land animal?", "Giraffe");

        flashcard.setPrompt("What is the world's smallest land animal?");
        assertEquals("What is the world's smallest land animal?", flashcard.getPrompt());
        flashcard.setAnswer("Etruscan Shrew");
        assertEquals("Etruscan Shrew", flashcard.getAnswer());
    }

    @Test
    void testGetters() {
        Flashcard flashcard = new Flashcard("What is the world's smallest flowering plant?", "Watermeal");
        assertEquals("What is the world's smallest flowering plant?", flashcard.getPrompt());
        assertEquals("Watermeal", flashcard.getAnswer());
        assertFalse(flashcard.getReviewStatus());
    }
}
