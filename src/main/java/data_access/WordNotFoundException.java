package data_access;

public class WordNotFoundException extends RuntimeException {
    public WordNotFoundException(String message) {
        super(message);
    }
}
