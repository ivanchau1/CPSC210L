package test.model.question;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MultipleChoiceQuestionTest extends QuestionTest {
    
    MultipleChoiceQuestion mc;
    String question1;

    @BeforeEach
    void setUp() {
        StringBuilder sb = new StringBuilder();
        sb.append("What is the three digits for this course?\n");
        sb.append("a) 210\n");
        sb.append("b) 110\n");
        sb.append("c) 121\n");
        sb.append("d) 221\n");
        question1 =sb.toString();
        mc = new MultipleChoiceQuestion(2, question1, 'a');
        question = mc;
    }

    @Test
    void testConstructor() {
        assertEquals(2, mc.getMaxMark());
    }

    @Test
    void testQuestionString() {
        assertEquals(question1 +"\n[2 points]", mc.getQuestionString());
    }

    @Test
    void testCheckAnswerCorrect() {
        assertTrue(mc.isCorrect("a"));
        assertTrue(mc.isCorrect("A"));
    }

    @Test
    void testCheckAnswerIncorrectCharacter() {
        assertFalse(mc.isCorrect("b"));
        assertFalse(mc.isCorrect("c"));
        assertFalse(mc.isCorrect("d"));
        assertFalse(mc.isCorrect("e"));
    }

    @Test
    void testCheckAnswerIncorrectString() {
        assertFalse(mc.isCorrect("hello"));
        assertFalse(mc.isCorrect("false"));
    }
}
