package common.exception;

/**
 * Created by boyce on 2014/7/16.
 */
public class EmptyQueueException extends  RuntimeException {

    public EmptyQueueException() {
    }

    public EmptyQueueException(String message) {
        super(message);
    }

    public EmptyQueueException(String message, Throwable cause) {
        super(message, cause);
    }

    public EmptyQueueException(Throwable cause) {
        super(cause);
    }

    public EmptyQueueException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
