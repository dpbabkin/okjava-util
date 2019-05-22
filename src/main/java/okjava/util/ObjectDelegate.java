package okjava.util;

import static okjava.util.NotNull.notNull;

/**
 * @author Dmitry Babkin dpbabkin@gmail.com
 * 10/16/2016
 * 21:55.
 */
public abstract class ObjectDelegate<D> {

    private final D delegate;

    public ObjectDelegate(D delegate) {
        this.delegate = notNull(delegate);
    }

    public D getDelegate() {
        return delegate;
    }

    @Override
    public int hashCode() {
        return delegate.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof ObjectDelegate<?>) {
            return delegate.equals(((ObjectDelegate<?>) o).delegate);
        }
        return delegate.equals(o);
    }

    @Override
    public String toString() {
        return delegate.toString();
    }
}
