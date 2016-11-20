package okjava.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Dmitry Babkin dpbabkin@gmail.com
 *         10/16/2016
 *         22:00.
 */
public class ObjectDelegateWithLogger<D> extends ObjectDelegate<D> {
    private final Logger logger;

    public ObjectDelegateWithLogger(D delegate) {
        super(delegate);
        this.logger = LoggerFactory.getLogger(delegate.getClass());
    }

    protected Logger getLogger() {
        return logger;
    }
}
