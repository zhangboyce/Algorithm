package common.exception;

/**
 * Created by boyce on 2014/7/16.
 */
public class EmptyStackException extends RuntimeException {

    public EmptyStackException() {
    }

    public EmptyStackException(String message) {
        super(message);
    }

    public EmptyStackException(String message, Throwable cause) {
        super(message, cause);
    }

    public EmptyStackException(Throwable cause) {
        super(cause);
    }

    public EmptyStackException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
