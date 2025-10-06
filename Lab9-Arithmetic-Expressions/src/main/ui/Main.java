package ui;

import expression.*;
import expression.exception.ExpressionNotValidException;
import java.util.Scanner;
import parser.ExpressionParser;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Enter expression to evaluate, or \"quit\" to exit:");
            String input = scanner.nextLine();
            if (input.equalsIgnoreCase("quit")) {
                break;
            }
            Expression expression = null;
            try {
                // Uncomment to test your code interactively. Also uncomment parser.ExpressionParser
                expression = ExpressionParser.parseExpression(input.trim());
                System.out.println(expression + " evaluates to " + expression.getValue());
            } catch (UnsupportedOperationException e) {
                System.out.println("Cannot divide by 0");
            } catch (ExpressionNotValidException e) {
                System.out.println("Expression cannot be evaluated: " + expression);
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
        scanner.close();
    }
}
