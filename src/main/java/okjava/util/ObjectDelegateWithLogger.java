package okjava.util;

import okjava.util.logger.LoggerUtils;
import org.slf4j.Logger;

/**
 * @author Dmitry Babkin dpbabkin@gmail.com
 * 10/16/2016
 * 22:00.
 */
public class ObjectDelegateWithLogger<D> extends ObjectDelegate<D> {
    private final Logger logger;

    public ObjectDelegateWithLogger(D delegate) {
        super(delegate);
        this.logger = LoggerUtils.createLogger(delegate.getClass());
    }

    protected Logger getLogger() {
        return logger;
    }
}
