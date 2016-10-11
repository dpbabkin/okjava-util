package okjava.util;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import org.junit.Test;

import java.util.Optional;

/**
 * @author Dmitry Babkin dpbabkin@gmail.com
 *         6/5/2016
 *         20:15.
 */
public class OptionalUtilsTest {

    @Test
    public void testBothEmpty() {

        Optional<String> optional = OptionalUtils.or(Optional.empty(), Optional.empty());
        assertThat(optional.isPresent(), is(Boolean.FALSE));
    }

    @Test
    public void testFirstEmpty() {

        Optional<String> optional = OptionalUtils.or(Optional.empty(), Optional.of("A"));
        assertThat(optional.isPresent(), is(Boolean.TRUE));
        assertThat(optional.get(), is("A"));
    }

    @Test
    public void testFirstPresent() {

        Optional<String> optional = OptionalUtils.or(Optional.of("B"), Optional.of("A"));
        assertThat(optional.isPresent(), is(Boolean.TRUE));
        assertThat(optional.get(), is("B"));
    }

}

