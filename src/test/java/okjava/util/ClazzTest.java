package okjava.util;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

import org.junit.Test;

import java.util.List;
import java.util.Set;

/**
 * @author Dmitry Babkin dpbabkin@gmail.com
 *         6/5/2016
 *         00:20.
 */
public class ClazzTest {

    @Test
    public void test() {
        Class<List<Set<String>>> clazz = Clazz.cast(List.class);
        assertThat(clazz, is(notNullValue()));
    }


    @Test(expected = ClassCastException.class)
    public void testWithClassCastException() {
        Long l = 0L;
        String string = Clazz.cast(l);
    }

    @Test(expected = ClassCastException.class)
    public void testWithClassCastException2() {
        Long l = 0L;
        String string = Clazz.cast(l, String.class);
    }

    @Test
    public void test02() {
        Long l = 0L;
        Number string = Clazz.cast(l, Number.class);
    }
}
