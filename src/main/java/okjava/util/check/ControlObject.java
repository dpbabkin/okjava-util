package okjava.util.check;

import okjava.util.NotNull;
import okjava.util.has.HasStringId;

import java.util.function.Supplier;

/**
 * @author Dmitry Babkin dpbabkin@gmail.com
 * 10/14/2016
 * 23:46.
 */
public final class ControlObject implements HasStringId, Supplier<String> {

    private final String id;


    private ControlObject(String id) {
        this.id = NotNull.notNull(id);
    }

    static ControlObject create(String id) {
        return new ControlObject(id);
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String get() {
        return getId();
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ControlObject that = (ControlObject) o;

        return id.equals(that.id);
    }

    @Override
    public String toString() {
        return "ControlObject{id='" + id + "'}";
    }
}
