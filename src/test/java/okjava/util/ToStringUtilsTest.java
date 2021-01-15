package okjava.util;

import com.google.common.collect.ImmutableList;
import okjava.util.string.ToStringUtils;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

/**
 * @author Dmitry Babkin dpbabkin@gmail.com
 * 6/5/2016
 * 20:23.
 */
public class ToStringUtilsTest {


    @Test
    public void testI2SNUll() {
        List<String> list = null;
        String toString = ToStringUtils.i2s(list);
        assertThat(toString, is("null"));
    }

    @Test
    public void testI2S() {
        List<String> list = ImmutableList.of("A", "B", "C", "D");
        String toString = ToStringUtils.i2s(list);

        assertThat(toString, is("A B C D"));
    }

    @Test
    public void testIS2S() {
        List<String> list = ImmutableList.of("A", "B", "C", "D");
        String toString = ToStringUtils.is2s(list);

        assertThat(toString, is("A B C D"));
    }

    @Test
    public void testIS2SWIthSeparator() {
        List<String> list = ImmutableList.of("A", "B", "C", "D");
        String toString = ToStringUtils.is2s(list, ", ");

        assertThat(toString, is("A, B, C, D"));
    }

    @Test
    public void testI2SWIthSeparator() {
        List<String> list = ImmutableList.of("A", "B", "C", "D");
        String toString = ToStringUtils.i2s(list, "|");

        assertThat(toString, is("A|B|C|D"));
    }

    @Test
    public void testI2SWIthMapper() {
        List<String> list = ImmutableList.of("A", "BBB", "CC", "DDDDDD");
        String toString = ToStringUtils.i2s(list, s -> {
            return String.valueOf(s.length());
        });

        assertThat(toString, is("1 3 2 6"));
    }

}
