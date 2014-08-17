package common.exception;

/**
 * Created by boyce on 2014/7/16.
 */
public class OutOfQueueException extends  RuntimeException {

    public OutOfQueueException() {
    }

    public OutOfQueueException(String message) {
        super(message);
    }

    public OutOfQueueException(String message, Throwable cause) {
        super(message, cause);
    }

    public OutOfQueueException(Throwable cause) {
        super(cause);
    }

    public OutOfQueueException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
