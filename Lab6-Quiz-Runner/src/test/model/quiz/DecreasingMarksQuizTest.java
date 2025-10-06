package test.model.quiz;

import model.exceptions.*;
import model.question.QuestionList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class DecreasingMarksQuizTest extends QuizTest {

    QuestionList questionList;
    DecreasingMarksQuiz decreasingMarkQuiz;

    @BeforeEach
    void setUp() {
        questionList = generateQuestionList();
        decreasingMarkQuiz = new DecreasingMarksQuiz(questionList, 2);
        quiz = decreasingMarkQuiz;
    }

    @Test
    void testConstructor() {
        assertEquals(0, decreasingMarkQuiz.getMarkSoFar());
        assertTrue(decreasingMarkQuiz.hasMoreQuestions());
        assertEquals(6, decreasingMarkQuiz.getMaxMark());
        assertEquals(2, decreasingMarkQuiz.getRemainingAttempts());
        assertEquals(2, decreasingMarkQuiz.getMaxAttempts());
        super.testConstructor();
    }

    @Test
    void testSubmitAnswerAllCorrect() {
        try {
            assertEquals(2, decreasingMarkQuiz.getRemainingAttempts());
            assertEquals(2, decreasingMarkQuiz.getMaxAttempts());
            decreasingMarkQuiz.getNextQuestion();
            String feedback = decreasingMarkQuiz.submitAnswer("Earth");
            assertEquals("Correct!", feedback);
            assertEquals(4, decreasingMarkQuiz.getMarkSoFar());
            assertEquals(1, decreasingMarkQuiz.getRemainingAttempts());
            assertEquals(2, decreasingMarkQuiz.getMaxAttempts());
            assertEquals(4, decreasingMarkQuiz.getMarkSoFar());
            decreasingMarkQuiz.getNextQuestion();
            assertEquals(2, decreasingMarkQuiz.getRemainingAttempts());
            assertEquals(2, decreasingMarkQuiz.getMaxAttempts());
            feedback = decreasingMarkQuiz.submitAnswer("Canada");
            assertEquals("Correct!", feedback);
            assertEquals(6, decreasingMarkQuiz.getMarkSoFar());
            assertEquals(1, decreasingMarkQuiz.getRemainingAttempts());
            assertEquals(2, decreasingMarkQuiz.getMaxAttempts());
            assertEquals(6, decreasingMarkQuiz.getMarkSoFar());
            assertEquals(6, decreasingMarkQuiz.getMaxMark());
            assertFalse(decreasingMarkQuiz.hasMoreQuestions());
            assertEquals("Your final mark is: 6 out of 6", decreasingMarkQuiz.endQuiz());
        } catch (Exception e) {
            fail("Should not have thrown exception.");
        }
    }

    @Test
    void testSubmitAnswerOutOfAttempts() {
        try {
            decreasingMarkQuiz.getNextQuestion();
            assertEquals(2, decreasingMarkQuiz.getRemainingAttempts());
            assertEquals(2, decreasingMarkQuiz.getMaxAttempts());
            String feedback = decreasingMarkQuiz.submitAnswer("Erth");
            assertEquals(1, decreasingMarkQuiz.getRemainingAttempts());
            assertEquals(2, decreasingMarkQuiz.getMaxAttempts());
            fail("Should have thrown exception");
        } catch (AnswerIncorrectException e) {
            try {
                assertEquals(1, decreasingMarkQuiz.getRemainingAttempts());
                String feedback = decreasingMarkQuiz.submitAnswer("Earh");
                fail("Should have thrown exception");
            } catch (OutOfTriesException ex) {
                // pass
                assertEquals(0, decreasingMarkQuiz.getRemainingAttempts());
                assertEquals(2, decreasingMarkQuiz.getMaxAttempts());
                assertEquals(0, decreasingMarkQuiz.getMarkSoFar());
                assertTrue(decreasingMarkQuiz.hasMoreQuestions());
            } catch (Exception ex) {
                fail("Should not have thrown this exception");
            }
        } catch (Exception e) {
            fail("Should not have thrown this exception");
        }
        assertEquals(0, decreasingMarkQuiz.getRemainingAttempts());
        assertTrue(decreasingMarkQuiz.hasMoreQuestions());
        assertEquals(0, decreasingMarkQuiz.getMarkSoFar());
        assertEquals(6, decreasingMarkQuiz.getMaxMark());
    }

    @Test
    void testSubmitAnswerPartiallyCorrect() {
        try {
            decreasingMarkQuiz.getNextQuestion();
            String feedback = decreasingMarkQuiz.submitAnswer("Earth");
            assertEquals("Correct!", feedback);
            assertEquals(4, decreasingMarkQuiz.getMarkSoFar());
            decreasingMarkQuiz.getNextQuestion();
            feedback = decreasingMarkQuiz.submitAnswer("erth");
            fail("Should have thrown exception");
        } catch (Exception e) {
            try {
                String feedback = decreasingMarkQuiz.submitAnswer("Earh");
                assertEquals(4, decreasingMarkQuiz.getMarkSoFar());
                fail("Should have thrown exception");
            } catch (Exception ex) {
                assertEquals(0, decreasingMarkQuiz.getRemainingAttempts());
                assertEquals(2, decreasingMarkQuiz.getMaxAttempts());
                try {
                    String feedback = decreasingMarkQuiz.submitAnswer("Earh");
                    assertEquals(4, decreasingMarkQuiz.getMarkSoFar());
                    fail("Should have thrown exception");
                } catch (OutOfTriesException exc) {
                    // pass
                    assertEquals(0, decreasingMarkQuiz.getRemainingAttempts());
                    assertEquals(2, decreasingMarkQuiz.getMaxAttempts());
                    assertEquals(4, decreasingMarkQuiz.getMarkSoFar());
                    assertFalse(decreasingMarkQuiz.hasMoreQuestions());
                    assertEquals("Your final mark is: 4 out of 6", decreasingMarkQuiz.endQuiz());
                } catch (Exception exc) {
                    fail("Should not have thrown this exception");
                }
            }
        }
        assertEquals(0, decreasingMarkQuiz.getRemainingAttempts());
        assertFalse(decreasingMarkQuiz.hasMoreQuestions());
        assertEquals(4, decreasingMarkQuiz.getMarkSoFar());
        assertEquals(6, decreasingMarkQuiz.getMaxMark());
    }

    @Test
    void testSubmitAnswerZeroMarksAvailable() {
        decreasingMarkQuiz = new DecreasingMarksQuiz(questionList, 5);
        try {
            decreasingMarkQuiz.getNextQuestion();
            String feedback = decreasingMarkQuiz.submitAnswer("Earth");
            assertEquals("Correct!", feedback);
            assertEquals(4, decreasingMarkQuiz.getMarkSoFar());
            decreasingMarkQuiz.getNextQuestion();
            feedback = decreasingMarkQuiz.submitAnswer("cad");
            fail("Should have thrown exception");
        } catch (Exception e) {
            try {
                String feedback = decreasingMarkQuiz.submitAnswer("cad");
                assertEquals(4, decreasingMarkQuiz.getMarkSoFar());
                fail("Should have thrown exception");
            } catch (Exception ex) {
                assertEquals(3, decreasingMarkQuiz.getRemainingAttempts());
                assertEquals(5, decreasingMarkQuiz.getMaxAttempts());
                try {
                    String feedback = decreasingMarkQuiz.submitAnswer("cad");
                    assertEquals(4, decreasingMarkQuiz.getMarkSoFar());
                    fail("Should have thrown exception");
                } catch (OutOfTriesException exc) {
                    fail("Should not have thrown this exception");
                } catch (AnswerIncorrectException exc) {
                    try {
                        String feedback = decreasingMarkQuiz.submitAnswer("Canada");
                        assertEquals("Correct!", feedback);
                        assertEquals(4, decreasingMarkQuiz.getMarkSoFar());
                        assertFalse(decreasingMarkQuiz.hasMoreQuestions());
                        assertEquals("Your final mark is: 4 out of 6", decreasingMarkQuiz.endQuiz());
                    } catch (OutOfTriesException exce) {
                        fail("Should not have thrown this exception");
                    } catch (AnswerIncorrectException exce) {
                        fail("Should not have thrown this exception");
                    }
                }
            }
        }
        assertEquals(6, decreasingMarkQuiz.getMaxMark());
    }

    @Test
    void testSubmitAnswerPartialMarks() {
        try {
            decreasingMarkQuiz.getNextQuestion();
            String feedback = decreasingMarkQuiz.submitAnswer("Earth");
            assertEquals("Correct!", feedback);
            assertEquals(4, decreasingMarkQuiz.getMarkSoFar());
            decreasingMarkQuiz.getNextQuestion();
            feedback = decreasingMarkQuiz.submitAnswer("erth");
            fail("Should have thrown exception");
        } catch (Exception e) {
            try {
                String feedback = decreasingMarkQuiz.submitAnswer("Canada");
                assertEquals("Correct!", feedback);
                assertEquals(5, decreasingMarkQuiz.getMarkSoFar());
                assertFalse(decreasingMarkQuiz.hasMoreQuestions());
                assertEquals("Your final mark is: 5 out of 6", decreasingMarkQuiz.endQuiz());
            } catch (Exception ex) {
                fail("Should not have thrown exception");
            }
        }
        assertEquals(0, decreasingMarkQuiz.getRemainingAttempts());
        assertFalse(decreasingMarkQuiz.hasMoreQuestions());
        assertEquals(5, decreasingMarkQuiz.getMarkSoFar());
    }

    @Test
    void testSubmitAnswerPartialCorrect() {
        assertEquals(2, decreasingMarkQuiz.getMaxAttempts());
        assertEquals(2, decreasingMarkQuiz.getRemainingAttempts());
        try {
            decreasingMarkQuiz.getNextQuestion();
            assertEquals(2, decreasingMarkQuiz.getRemainingAttempts());
            String feedback = decreasingMarkQuiz.submitAnswer("Erth");
            fail("Should have thrown exception");
        } catch (AnswerIncorrectException e) {
            try {
                assertEquals(1, decreasingMarkQuiz.getRemainingAttempts());
                String feedback = decreasingMarkQuiz.submitAnswer("Erth");
                fail("Should have thrown exception");
            } catch (AnswerIncorrectException ex) {
                fail("Should not have thrown this exception");
            } catch (OutOfTriesException ex) {
                // pass
                assertEquals(0, decreasingMarkQuiz.getRemainingAttempts());
            }
        } catch (OutOfTriesException e) {
            fail("Should not have thrown this exception");
        }
        assertEquals(0, decreasingMarkQuiz.getRemainingAttempts());
        assertEquals(0, decreasingMarkQuiz.getMarkSoFar());

        try {
            decreasingMarkQuiz.getNextQuestion();
            assertEquals(2, decreasingMarkQuiz.getRemainingAttempts());
            String feedback = decreasingMarkQuiz.submitAnswer("cad");
            fail("Should have thrown exception");
        } catch (AnswerIncorrectException e) {
            try {
                assertEquals(1, decreasingMarkQuiz.getRemainingAttempts());
                String feedback = decreasingMarkQuiz.submitAnswer("canada");
                assertEquals("Correct!", feedback);
                assertEquals(0, decreasingMarkQuiz.getRemainingAttempts());
                assertEquals(1, decreasingMarkQuiz.getMarkSoFar());
                assertFalse(decreasingMarkQuiz.hasMoreQuestions());
                assertEquals("Your final mark is: 1 out of 6", decreasingMarkQuiz.endQuiz());
            } catch (AnswerIncorrectException ex) {
                fail("Should not have thrown exception");
            } catch (OutOfTriesException ex) {
                fail("Should not have thrown exception");
            }
        } catch (OutOfTriesException e) {
            fail("Should not have thrown this exception");
        }
    }
}
