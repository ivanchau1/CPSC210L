package expression;

import expression.exception.ExpressionNotValidException;

// Represents an addition expression having an arbitrary number of subexpressions
// and/or values
public class AdditionExpression extends AbstractArithmeticExpression {

    private static final String OPERATOR = "+";

    // EFFECTS: constructs an addition expression having no sub-expressions or values
    public AdditionExpression() {
        super();
    }

    // TODO: complete implementation
    // EFFECTS: returns value of expression
    // throws ExpressionNotValidException if expression does not have at least two operands
    // throws java.lang.UnsupportedOperationException if division by zero occurs
    public int getValue() {
        if (expressions.size() < 2) {
            throw new ExpressionNotValidException();
        }
        
        int result = 0;
        for (Expression e : expressions) {
            result += e.getValue();
        }

        return result;
    }

    // EFFECTS: returns string representation of expression
    // prepends "!!! " to any expression that does not have at least two operands
    // 
    // Example: when this method is called on the expression (+ (* 5)) we expect 
    // the following string to be produced 
    // !!! (+ !!! (* 5))
    // as neither the + nor the * operator has at least two operands 
    public String toString() {
        String result = "";
        String bangBang = "";
        if (expressions.size() < 2) {
            bangBang = "!!! "; 
        }

        for (Expression e : expressions) {
            result = result + " " + e.toString();
        }
        
        return bangBang + "(" + OPERATOR + result + ")";
    }
}
