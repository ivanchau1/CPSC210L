package expression;

import java.util.ArrayList;
import java.util.List;

// Represents an expression having an arbitrary
// number of subexpressions and/or values
public abstract class AbstractArithmeticExpression implements Expression {

    protected List<Expression> expressions;

    // EFFECTS: constructs an expression having no sub-expressions or values
    public AbstractArithmeticExpression() {
        // stub

        expressions = new ArrayList<>();
    }

    // TODO: complete implementation
    public void addSubexpression(Expression e) {
        expressions.add(e);
    }
}
