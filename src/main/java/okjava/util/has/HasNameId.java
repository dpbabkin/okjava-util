package okjava.util.has;

/**
 * @author Dmitry Babkin dpbabkin@gmail.com
 * 6/22/2016
 * 20:53.
 */
public interface HasNameId extends HasName, HasStringId {

    @Override
    default String getId() {
        return getName();
    }
}
