package okjava.util;

import org.junit.Test;

import java.util.ArrayList;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

/**
 * @author Dmitry Babkin dpbabkin@gmail.com
 * 6/4/2016
 * 23:48.
 */
public class ParseNumberTest {

    @Test
    public void parseInteger() {

        Integer i = 1;
        assertThat(ParseNumber.parseInt(i).get(), is(1));
        Long l = 1L;
        assertThat(ParseNumber.parseInt(l).get(), is(1));

        Double d = 1.0;
        assertThat(ParseNumber.parseInt(d).get(), is(1));
        assertThat(ParseNumber.parseInt("1").get(), is(1));

        assertThat(ParseNumber.parseInt("BAD").isPresent(), is(Boolean.FALSE));
        assertThat(ParseNumber.parseInt(null).isPresent(), is(Boolean.FALSE));
        assertThat(ParseNumber.parseInt(new ArrayList<>()).isPresent(), is(Boolean.FALSE));
    }

    @Test
    public void parseLong() {

        Integer i = 1;
        assertThat(ParseNumber.parseLong(i).get(), is(1L));
        Long l = 1L;
        assertThat(ParseNumber.parseLong(l).get(), is(1L));

        Double d = 1.0;
        assertThat(ParseNumber.parseLong(d).get(), is(1L));
        assertThat(ParseNumber.parseLong("1").get(), is(1L));

        assertThat(ParseNumber.parseLong("BAD").isPresent(), is(Boolean.FALSE));
        assertThat(ParseNumber.parseLong(null).isPresent(), is(Boolean.FALSE));
        assertThat(ParseNumber.parseLong(new ArrayList<>()).isPresent(), is(Boolean.FALSE));
    }
}
