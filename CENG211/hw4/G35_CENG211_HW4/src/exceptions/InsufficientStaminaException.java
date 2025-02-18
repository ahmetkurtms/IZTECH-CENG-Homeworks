package exceptions;

public class InsufficientStaminaException extends RuntimeException {
    public InsufficientStaminaException() {
        super("Insufficient stamina to perform this action.");
    }
}
