package okjava.util;

import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Maps.newHashMap;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasEntry;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;

import com.google.common.collect.Lists;

import org.junit.Test;

import java.util.List;
import java.util.Map;

/**
 * @author Dmitry Babkin dpbabkin@gmail.com
 *         6/5/2016
 *         00:11.
 */
public class NotEmptyTest {

    @Test(expected = NullPointerException.class)
    public void testStringIsNull() {
        String s = null;
        NotEmpty.check(s);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testStringIsEmpty() {
        NotEmpty.check("");
    }

    @Test
    public void testStringIsOK() {
        assertThat(NotEmpty.check("A"), is("A"));
    }


    @Test(expected = NullPointerException.class)
    public void testCollectionIsNull() {
        List<String> l = null;
        NotEmpty.check(l);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCollectionIsEmpty() {
        NotEmpty.check(newArrayList());
    }

    @Test
    public void testCollectionIsOK() {
        assertThat(NotEmpty.check(newArrayList("A")), hasSize(1));
        assertThat(NotEmpty.check(newArrayList("A")), hasItem("A"));
    }

    @Test(expected = NullPointerException.class)
    public void testIterableIsNull() {
        List<String> l = null;
        NotEmpty.check((Iterable<String>) l);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testIterableIsEmpty() {
        NotEmpty.check((Iterable<String>) Lists.<String>newArrayList());
    }

    @Test
    public void testIterableIsOK() {
        NotEmpty.check((Iterable<String>) Lists.newArrayList("A"));
    }

    @Test(expected = NullPointerException.class)
    public void tesMapIsNull() {
        Map<String, Integer> m = null;
        NotEmpty.check(m);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testMapIsEmpty() {
        NotEmpty.check(newHashMap());
    }

    @Test
    public void testMapIsOK() {
        Map<String, Long> m = newHashMap();
        m.put("A", 1L);
        assertThat(NotEmpty.check(m).entrySet(), hasSize(1));
        assertThat(NotEmpty.check(m), hasEntry("A", 1L));
    }
}
