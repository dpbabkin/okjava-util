package okjava.util.has;

/**
 * @author Dmitry Babkin dpbabkin@gmail.com
 * 10/8/2015
 * 21:48.
 */
public interface HasId<ID> {
    ID getId();

    default String getStringId() {
        return getId().toString();
    }
}
