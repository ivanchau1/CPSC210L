package test.model.question.checker;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MultipleChoiceAnswerCheckerTest {

    MultipleChoiceAnswerChecker mcc;

    @BeforeEach
    void setUp() {
        mcc = new MultipleChoiceAnswerChecker('a');
    }

    @Test
    void testCorrectAnswerCaseSensitive() {
        assertTrue(mcc.checkAnswer("a"));
    }

    @Test
    void testCorrectAnswerCaseInsensitive() {
        assertTrue(mcc.checkAnswer("A"));
    }

    @Test
    void testIncorrectAnswer() {
        assertFalse(mcc.checkAnswer("b"));
        assertFalse(mcc.checkAnswer("c"));
        assertFalse(mcc.checkAnswer("d"));
    }

    @Test
    void voidIncorrectAnswerString() {
        assertFalse(mcc.checkAnswer("hello"));
        assertFalse(mcc.checkAnswer("false"));
        assertFalse(mcc.checkAnswer("apple"));
    }
}
