package okjava.util.cache;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import java.util.function.IntFunction;

/**
 * @author Dmitry Babkin dpbabkin@gmail.com
 *         1/10/2017
 *         23:12.
 */
public class IntToObjectCacheTest {


    @Test
    public void test01() {

        IntFunction<String> intToObjectCache = IntToObjectCache.create(String::valueOf, 0, 10);

        for (int i = 0; i <= 10; i++) {
            assertThat("" + i, is(intToObjectCache.apply(i)));
        }
    }
}
