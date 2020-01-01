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
public class LongTimeSequenceIdUtilsTest {

    private static final long TIME_LONG = 1557178359371L;
    private static final long ID = 1654529297522622464L; // "20200101:123456.789_0"

    @Test
    public void test001() {
        String result = LongTimeSequenceIdUtils.format(ID);
        assertThat(result, is("20200101:123456.789_0"));
    }

    @Test
    public void test002() {
        String result = LongTimeSequenceIdUtils.format(TIME_LONG, 5);
        assertThat(result, is("20190506:213239.371_5"));
    }

    @Test
    public void test003() {
        String id = "20190506:213239.371_5";

        long result = TimeSequenceIdFormat.parse(id);
        assertThat(LongTimeSequenceIdUtils.fetchTime(result), is(TIME_LONG));
        assertThat(LongTimeSequenceIdUtils.fetchSequence(result), is(5L));
    }

    @Test
    public void test004() {
        long id = TimeSequenceIdGeneratorFactory.timeSequenceIdGenerator().generate();

        String formattedString = LongTimeSequenceIdUtils.format(id);
        long newId = TimeSequenceIdFormat.parse(formattedString);
        System.out.println("id=" + id + " newId=" + newId);
        assertThat(id, is(newId));
    }

    @Test
    public void test005() {
        TimeSequenceId timeSequenceId = TimeSequenceIdGeneratorFactory.withMapper(l -> TimeSequenceIdFactory.timeSequenceIdFactory().fromLong(l)).generate();

        String formattedString = LongTimeSequenceIdUtils.format(timeSequenceId);
        TimeSequenceId newTimeSequenceId = LongTimeSequenceIdUtils.parseToTimeSequenceId(formattedString);
        System.out.println("timeSequenceId=" + timeSequenceId + " newTimeSequenceId=" + newTimeSequenceId);
        assertThat(timeSequenceId, is(newTimeSequenceId));
    }

    @Test
    public void test06() {
        long startTime = System.currentTimeMillis();
        for (; ; ) {
            if (System.currentTimeMillis() - startTime >= 100) {
                return;
            }
            test004();
            test005();
        }
    }


    @Test(expected = IllegalArgumentException.class)
    public void test007() {
        LongTimeSequenceIdUtils.parse("20190506:213239.371_XXX");
    }

    @Test(expected = IllegalArgumentException.class)
    public void test008() {
        LongTimeSequenceIdUtils.parse("20190506XX:213239.371_5");
    }

    @Test(expected = IllegalArgumentException.class)
    public void test009() {
        LongTimeSequenceIdUtils.parse("20190506:213239.371");
    }
}
