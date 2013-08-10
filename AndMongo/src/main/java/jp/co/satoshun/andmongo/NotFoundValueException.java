package jp.co.satoshun.andmongo;


public class NotFoundValueException extends IllegalArgumentException {
    public NotFoundValueException(String message) {
        super(message);
    }

    public NotFoundValueException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
