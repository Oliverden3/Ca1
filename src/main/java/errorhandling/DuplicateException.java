package errorhandling;

public class DuplicateException extends Exception {
    public DuplicateException(String errorMessage) {
        super(errorMessage);
    }
}
