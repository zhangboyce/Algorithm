package common.exception;

/**
 * Created by boyce on 2014/7/16.
 */
public class EmptyTreeException extends  RuntimeException {

    public EmptyTreeException() {
    }

    public EmptyTreeException(String message) {
        super(message);
    }

    public EmptyTreeException(String message, Throwable cause) {
        super(message, cause);
    }

    public EmptyTreeException(Throwable cause) {
        super(cause);
    }

    public EmptyTreeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
