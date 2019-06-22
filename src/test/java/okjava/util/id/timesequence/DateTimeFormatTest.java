package okjava.util.id.timesequence;

import okjava.util.id.TimeSequenceIdGeneratorFactory;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

/**
 * @author Dmitry Babkin dpbabkin@gmail.com
 * 5/6/2019
 * 23:30.
 */
public class DateTimeFormatTest {


    @Test
    public void test001() {
        long time = 1557178359371L;

        String result = TimeSequenceIdFormat.longTimeToString(time);

        assertThat(result, is("20190506:213239.371"));

    }

}
