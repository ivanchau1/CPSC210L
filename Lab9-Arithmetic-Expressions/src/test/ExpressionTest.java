package expression;

import expression.exception.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.*;

class ExpressionTest {

    DivisionExpression div; 
    AdditionExpression add;
    MultiplicationExpression mult;
    SubtractionExpression sub;
    Value v1;
    Value v2;
    Value v3;

    @BeforeEach
    void setUp() {
        add = new AdditionExpression();
        div = new DivisionExpression();
        mult = new MultiplicationExpression();
        sub = new SubtractionExpression();
        v1 = new Value(9);
        v2 = new Value(0);
        v3 = new Value(-1);
    }

    @Test
    void testAddition() {
        add.addSubexpression(new Value(9));
        add.addSubexpression(new Value(3));

        assertEquals(12, add.getValue());
        assertEquals("(+ 9 3)", add.toString());
    }

    @Test
    void testAdditionLessThanTwoOperands() {
        try {
            add.addSubexpression(new Value(9));
            assertEquals(12, add.getValue());
            fail();
        } catch (ExpressionNotValidException e) {
            // pass
            assertEquals("!!! (+ 9)", add.toString());
        }
    }

    @Test
    void testSubtraction() {
        sub.addSubexpression(v1);
        sub.addSubexpression(v3);

        assertEquals(10, sub.getValue());
        assertEquals("(- 9 -1)", sub.toString());
    }

    @Test
    void testSubtractionLessThanTwoOperands() {
        try {
            sub.addSubexpression(v1);
            assertEquals(12, sub.getValue());
            fail();
        } catch (ExpressionNotValidException e) {
            // pass
            assertEquals("!!! (- 9)", sub.toString());
        }
    }

    @Test
    void testMultiplication() {
        mult.addSubexpression(v1);
        mult.addSubexpression(v3);

        assertEquals(-9, mult.getValue());
        assertEquals("(* 9 -1)", mult.toString());
    }

    @Test
    void testMultiplcationLessThanTwoOperands() {
        try {
            mult.addSubexpression(v1);
            assertEquals(12, mult.getValue());
            fail();
        } catch (ExpressionNotValidException e) {
            // pass
            assertEquals("!!! (* 9)", mult.toString());
        }
    }

    @Test
    void testDivision() {
        div.addSubexpression(v1);
        div.addSubexpression(v3);

        assertEquals(-9, div.getValue());
        assertEquals("(/ 9 -1)", div.toString());
    }

    @Test
    void testDivisionLessThanTwoOperand() {
        try {
            div.addSubexpression(v1);
            assertEquals(12, div.getValue());
            fail();
        } catch (ExpressionNotValidException e) {
            // pass
            assertEquals("!!! (/ 9)", div.toString());
        }catch (UnsupportedOperationException e) {
            fail();
        }
    }

    @Test
    void testDivisionByZero() {
        try {
            div.addSubexpression(v1);
            div.addSubexpression(v2);
            assertEquals(12, div.getValue());
            fail();
        } catch (UnsupportedOperationException e) {
            // pass
            assertEquals("(/ 9 0)", div.toString());
        } catch (ExpressionNotValidException e) {
            fail();
        }
    }

    @Test
    void testDivisionAndAdditionCombination() {
        div.addSubexpression(v1);
        div.addSubexpression(new Value(3));

        add.addSubexpression(new Value(3));
        add.addSubexpression(div);

        assertEquals(6, add.getValue());
        assertEquals("(+ 3 (/ 9 3))", add.toString());
    }

    @Test
    void testCombinationOfEverything() {
        try {
            div.addSubexpression(v1);
            div.addSubexpression(new Value(3));

            add.addSubexpression(new Value(3));
            add.addSubexpression(div);

            sub.addSubexpression(v1);
            sub.addSubexpression(new Value(4));

            mult.addSubexpression(new Value(2));
            mult.addSubexpression(sub);
            mult.addSubexpression(add);

            assertEquals(60, mult.getValue());
            assertEquals("(* 2 (- 9 4) (+ 3 (/ 9 3)))", mult.toString());
        } catch (UnsupportedOperationException e) {
            fail();
        } catch (ExpressionNotValidException e) {
            fail();
        }
    }

    @Test
    void testExpressionLargerThanTwo() {
        add.addSubexpression(v1);
        add.addSubexpression(v2);
        add.addSubexpression(v3);

        assertEquals(8, add.getValue());
        assertEquals("(+ 9 0 -1)", add.toString());
    }

    @Test
    void testEmptyExpression() {
        try {
            add.getValue();
            fail();
        } catch (ExpressionNotValidException e) {
            // pass
            assertEquals("!!! (+)", add.toString());
        }
    }

    @Test
    void testDivisionTrauncation() {
        div.addSubexpression(new Value(9));
        div.addSubexpression(new Value(4));

        assertEquals(2, div.getValue());
        assertEquals("(/ 9 4)", div.toString());
    }
}
