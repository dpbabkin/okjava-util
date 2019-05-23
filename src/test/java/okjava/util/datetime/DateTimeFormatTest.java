package okjava.util.datetime;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import org.junit.Test;

/**
 * @author Dmitry Babkin dpbabkin@gmail.com
 * 5/6/2019
 * 23:39.
 */
public class DateTimeFormatTest {


    private final DateTimeFormat dateTimeFormat = DateTimeFormat.create("yyyyMMdd HHmmssSSS");


    @Test
    public void testLongToString() {
        long time = 1557178359371L;

        String result = dateTimeFormat.longToString(time);

        assertThat(result, is("20190506 213239371"));

    }

    @Test
    public void testStringToLong() {
        String time = "20190506 213239371";

        long result = dateTimeFormat.stringToLong(time);

        assertThat(result, is(result));

    }


}