package okjava.util.id;

import java.util.function.Supplier;

/**
 * @author Dmitry Babkin dpbabkin@gmail.com
 * 5/6/2019
 * 23:53.
 */
public interface IdGenerator<V> extends Supplier<V> {

    V generate();

    @Override
    default V get(){
        return generate();
    }
}
