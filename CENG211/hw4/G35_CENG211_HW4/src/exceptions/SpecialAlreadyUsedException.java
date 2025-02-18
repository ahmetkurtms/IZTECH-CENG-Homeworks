package exceptions;

public class SpecialAlreadyUsedException extends RuntimeException {  
    public SpecialAlreadyUsedException() {
        super("Special of the character has already been used.");
    }
}
