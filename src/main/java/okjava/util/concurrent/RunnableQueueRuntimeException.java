package okjava.util.concurrent;

/**
 * @author Dmitry Babkin dpbabkin@gmail.com
 *         11/26/2016
 *         17:30.
 */
public class RunnableQueueRuntimeException extends RuntimeException {

    RunnableQueueRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }
}
