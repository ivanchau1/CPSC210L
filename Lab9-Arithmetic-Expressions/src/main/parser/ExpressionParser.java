package parser;

import expression.*;
import java.util.ArrayList;
import java.util.List;

// A utility class for constructing AbstractArithmeticExpressions from Strings
public class ExpressionParser {

    // EFFECTS: Parses the given expression string into an Expression object
    // - If the expression is wrapped in parentheses, it is recursively parsed as an
    // arithmetic expression
    // - If the expression is an integer, it is parsed as a Value
    // - Throws an IllegalArgumentException if the expression is not a valid integer
    // or arithmetic expression
    public static Expression parseExpression(String expression) {
        if (expression.startsWith("(") && expression.endsWith(")")) {
            return parseArithmeticExpression(expression);
        }
        try {
            int integerLiteral = Integer.parseInt(expression);
            return new Value(integerLiteral);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Bad Value: " + expression);
        }
    }

    // REQUIRES: expression must start with '(' and end with ')'
    // EFFECTS: Parses an arithmetic expression by recursively parsing
    // sub-expressions
    // Throws an IllegalArgumentException if the expression is empty or contains
    // invalid syntax
    private static AbstractArithmeticExpression parseArithmeticExpression(String expression) {
        String withoutEnclosingParens = expression.substring(1, expression.length() - 1);
        List<String> arguments = extractSubexpressionStrings(withoutEnclosingParens);
        if (arguments.isEmpty()) {
            throw new IllegalArgumentException("Empty Expression");
        }
        AbstractArithmeticExpression result = constructExpression(arguments.get(0));
        for (int i = 1; i < arguments.size(); i++) {
            result.addSubexpression(parseExpression(arguments.get(i)));
        }
        return result;
    }

    // EFFECTS: Constructs an arithmetic expression object corresponding to the
    // given operator
    // Throws an IllegalArgumentException if the operator is invalid
    private static AbstractArithmeticExpression constructExpression(String expressionSymbol) {
        switch (expressionSymbol) {
            case "+":
                return new AdditionExpression();
            case "-":
                return new SubtractionExpression();
            case "*":
                return new MultiplicationExpression();
            case "/":
                return new DivisionExpression();
            default:
                throw new IllegalArgumentException("Illegal operator: " + expressionSymbol);
        }
    }

    // EFFECTS: Given a string of the form "a b (c (d e)) f (g)", returns a list
    // containing
    // {"a", "b", "(c (d e))", "f", "(g)"}
    private static List<String> extractSubexpressionStrings(String combinedString) {
        int balance = 0; // # of left parens encountered - # of right parens encountered
        StringBuilder builder = new StringBuilder();
        List<String> results = new ArrayList<>();

        for (char currentChar : combinedString.toCharArray()) {
            if (currentChar == '(') {
                balance++;
            } else if (currentChar == ')') {
                balance--;
            }

            if (currentChar == ' ' && balance == 0) {
                if (builder.length() > 0) {
                    results.add(builder.toString());
                    builder = new StringBuilder();
                }
            } else {
                builder.append(currentChar);
            }
        }

        if (builder.length() > 0) {
            results.add(builder.toString());
        }
        if (balance != 0) {
            throw new IllegalArgumentException("Mismatched parentheses in expression");
        }
        return results;
    }
    
}
