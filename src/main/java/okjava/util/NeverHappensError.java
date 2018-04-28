package okjava.util;

/**
 * @author Dmitry Babkin dpbabkin@gmail.com
 * 4/28/2018
 * 15:09.
 */
public class NeverHappensError extends Error {

    public NeverHappensError(String message) {
        super(message);
    }
}
