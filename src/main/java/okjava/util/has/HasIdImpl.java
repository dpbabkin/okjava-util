package okjava.util.has;

import okjava.util.string.ToStringBuffer;

import static okjava.util.NotNull.notNull;

/**
 * @author Dmitry Babkin dpbabkin@gmail.com
 * 6/10/2016
 * 18:52.
 */
public abstract class HasIdImpl<ID> implements HasId<ID> {

    private final ID id;

    public HasIdImpl(ID id) {
        this.id = notNull(id);
    }

    @Override
    public final ID getId() {
        return id;
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        HasIdImpl<?> hasId = (HasIdImpl<?>) o;
        return id.equals(hasId.id);
    }

    @Override
    public String toString() {
        return ToStringBuffer.of(this).
                add("id", id)
                .toString();
    }
}
