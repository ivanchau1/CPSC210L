package test.model.quiz;

import model.exceptions.*;
import model.question.QuestionList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class UnlimitedTriesQuizTest extends QuizTest {

    UnlimitedTriesQuiz utquiz;

    @BeforeEach
    void runBefore() {
        QuestionList questionList = generateQuestionList();
        utquiz = new UnlimitedTriesQuiz(questionList);
        quiz = utquiz;
    }

    @Test
    void testConstructor() {
        super.testConstructor();
        assertEquals(6, utquiz.getMaxMark());
    }

    @Test
    void testSubmitAnswerAllCorrect() {
        try {
            utquiz.getNextQuestion();
            String feedback = utquiz.submitAnswer("Earth");
            assertEquals("Correct!", feedback);
            assertEquals(4, utquiz.getMarkSoFar());
            assertEquals(1, utquiz.getNumAttempts());
            utquiz.getNextQuestion();
            feedback = utquiz.submitAnswer("Canada");
            assertEquals("Correct!", feedback);
            assertEquals(6, utquiz.getMarkSoFar());
            assertEquals(2, utquiz.getNumAttempts());
            assertFalse(utquiz.hasMoreQuestions());
            assertEquals("It took you 2 attempts to answer 2 questions correctly.", utquiz.endQuiz());
        } catch (Exception e) {
            fail("Should not have thrown exception.");
        }
    }

    @Test
    void testSubmitAnswerAllIncorrect() {
        try {
            utquiz.getNextQuestion();
            String feedback = utquiz.submitAnswer("erth");
            fail("Should have thrown exception");
        } catch (AnswerIncorrectException e) {
            // pass
            assertEquals(1, utquiz.getNumAttempts());
        } catch (Exception e) {
            fail("Should not have thrown this exception");
        }
        assertTrue(utquiz.hasMoreQuestions());
        assertEquals(0, utquiz.getMarkSoFar());
    }

    @Test
    void testSubmitAnswerAllIncorrectMultipleTimes() {
        try {
            utquiz.getNextQuestion();
            String feedback = utquiz.submitAnswer("erth");
            fail("Should have thrown exception");
        } catch (AnswerIncorrectException e) {
            assertEquals(1, utquiz.getNumAttempts());
            try {
                String feedback = utquiz.submitAnswer("erth");
                fail("Should have thrown exception");
            } catch (AnswerIncorrectException ex) {
                assertEquals(2, utquiz.getNumAttempts());
                try {
                    String feedback = utquiz.submitAnswer("erth");
                    fail("Should have thrown exception");
                } catch (AnswerIncorrectException exc) {
                    // pass
                    assertEquals(3, utquiz.getNumAttempts());
                    assertEquals(0, utquiz.getMarkSoFar());
                    assertEquals(6, utquiz.getMaxMark());
                    assertTrue(utquiz.hasMoreQuestions());
                } catch (Exception exc) {
                    fail("Should not have thrown this exception");
                }
            } catch (Exception ex) {
                fail("Should not have thrown this exception");
            }
        } catch (Exception e) {
            fail("Should not have thrown this exception");
        }
        assertTrue(utquiz.hasMoreQuestions());
        assertEquals(0, utquiz.getMarkSoFar());
    }

    @Test
    void testSubmitAnswerPartiallyCorrectIncomplete() {
        try {
            utquiz.getNextQuestion();
            String feedback = utquiz.submitAnswer("Earth");
            assertEquals("Correct!", feedback);
            assertEquals(1, utquiz.getNumAttempts());
            utquiz.getNextQuestion();
            feedback = utquiz.submitAnswer("cad");
            fail("Should have thrown exception");
        } catch (Exception e) {
            assertEquals(2, utquiz.getNumAttempts());
            try {
                String feedback = utquiz.submitAnswer("cad");
                fail("Should have thrown exception");
            } catch (Exception ex) {
                // pass
                assertEquals(3, utquiz.getNumAttempts());
            }
        }
        assertFalse(utquiz.hasMoreQuestions());
    }

    @Test
    void testSubmitAnswerPartiallyCorrectCompleted() {
        try {
            utquiz.getNextQuestion();
            String feedback = utquiz.submitAnswer("Earth");
            assertEquals("Correct!", feedback);
            assertEquals(4, utquiz.getMarkSoFar());
            utquiz.getNextQuestion();
            feedback = utquiz.submitAnswer("cad");
            fail("Should have thrown exception");
        } catch (AnswerIncorrectException e) {
            assertEquals(2, utquiz.getNumAttempts());
            try {
                String feedback = utquiz.submitAnswer("cad");
                fail("Should have thrown exception");
            } catch (Exception ex) {
                assertEquals(3, utquiz.getNumAttempts());
                try {
                    String feedback = utquiz.submitAnswer("canada");
                    assertEquals(6, utquiz.getMarkSoFar());
                    assertEquals(6, utquiz.getMaxMark());
                    assertEquals(4, utquiz.getNumAttempts());
                    assertFalse(utquiz.hasMoreQuestions());
                    assertEquals("It took you 4 attempts to answer 2 questions correctly.", quiz.endQuiz());
                } catch (Exception exc) {
                    fail("Should not have thrown exception");
                }
            }
        } catch (Exception e) {
            fail("Should not have thrown this exception");
        }
    }
}

