package expression.exception;

public class ExpressionNotValidException extends RuntimeException {
    public ExpressionNotValidException() {
        super();
    }


    public ExpressionNotValidException(String msg) {
        super(msg);
    }
}
