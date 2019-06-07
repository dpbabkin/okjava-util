package okjava.util.has;

import static java.util.Objects.requireNonNull;

/**
 * @author Dmitry Babkin dpbabkin@gmail.com
 * 6/10/2016
 * 18:52.
 */
public final class HasIdImpl<ID> implements HasId<ID> {

    private final ID id;

    public static <ID> HasId<ID> create(ID id) {
        return new HasIdImpl<>(id);
    }

    private HasIdImpl(ID id) {
        this.id = requireNonNull(id);
    }

    @Override
    public ID getId() {
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
        return this.getClass().getSimpleName() + "|id:" + id;
    }
}
