package expression;

public interface Expression {
    // EFFECTS: returns value of expression
    // throws ExpressionNotValidException if expression does not have at least two operands
    // throws java.lang.UnsupportedOperationException if division by zero occurs
    int getValue();


    // EFFECTS: returns string representation of expression
    // prepends "!!! " to any expression that does not have at least two operands
    // 
    // Example: when this method is called on the expression (+ (* 5)) we expect 
    // the following string to be produced 
    // !!! (+ !!! (* 5))
    // as neither the + nor the * operator has at least two operands 
    @Override
    String toString();
}
