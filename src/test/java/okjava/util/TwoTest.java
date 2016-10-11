package okjava.util;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.Test;

/**
 * @author Dmitry Babkin dpbabkin@gmail.com
 *         6/5/2016
 *         00:26.
 */
public class TwoTest {

    @Test
    public void testTwo() {
        Two<String, Long> two = Two.create("A", 1L);
        assertThat(two.getA(), is("A"));
        assertThat(two.getB(), is(1L));
    }


    @Test(expected = NullPointerException.class)
    public void testTwoDeclinesNullsA() {
        Two.create(null, 1L);
    }

    @Test(expected = NullPointerException.class)
    public void testTwoDeclinesNullsB() {
        Two.create("A", null);
    }

    @Test
    public void testTwoEqualsHashCode() {
        for (long l = 2; l < 1000_000_000_000L; l += l / 2) {
            for (int i = 0; i < 1000; i++) {

                Two<String, Long> a = Two.create(String.valueOf(i), l);
                Two<String, Long> b = Two.create(String.valueOf(i), l);
                assertThat(a.equals(b), is(Boolean.TRUE));
                assertThat(a.hashCode(), is(b.hashCode()));
            }
        }
    }

    @Test
    public void testTwoNotEquals() {
        for (long l = 2; l < 1000_000_000_000L; l += l / 2) {
            for (int i = 0; i < 1000; i++) {

                Two<String, Long> a = Two.create(String.valueOf(i), l);
                Two<String, Long> b = Two.create(String.valueOf(i), l - 1);
                Two<String, Long> c = Two.create(String.valueOf(i - 1), l);
                assertThat(a.equals(b), is(Boolean.FALSE));
                assertThat(a.equals(c), is(Boolean.FALSE));
                assertThat(b.equals(c), is(Boolean.FALSE));
            }
        }
    }

    @Test
    public void testTwoToString() {
        Two<String, Long> two = Two.create("A", 1L);
        assertThat(two.toString(), is("Two{a=A, b=1}"));

    }


}
