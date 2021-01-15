package okjava.util.cache;

import org.junit.Test;

import java.util.function.IntFunction;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

/**
 * @author Dmitry Babkin dpbabkin@gmail.com
 * 1/10/2017
 * 23:12.
 */
public class IntToObjectCacheTest {


    @Test
    public void testIntToObjectCacheImmutableFixed() {

        IntFunction<String> intToObjectCache = IntToObjectCacheImmutableFixed.create(String::valueOf, 0, 10);

        for (int i = 0; i <= 10; i++) {
            assertThat("" + i, is(intToObjectCache.apply(i)));
        }
    }

    @Test
    public void testIntToObjectCacheLazy() {

        IntFunction<String> intToObjectCache = IntToObjectCacheLazy.create(String::valueOf);

        for (int i = 0; i <= 10; i++) {
            assertThat("" + i, is(intToObjectCache.apply(i)));
        }
    }

    @Test
    public void testIntToObjectCacheLazyReversed() {

        IntFunction<String> intToObjectCache = IntToObjectCacheLazy.create(String::valueOf);

        for (int i = 10; i >= 0; i--) {
            assertThat("" + i, is(intToObjectCache.apply(i)));
        }
    }
}
