package okjava.util.id.format;

import okjava.util.id.TimeSequenceIdGeneratorFactory;
import okjava.util.id.timesequence.TimeSequenceId;
import okjava.util.id.timesequence.TimeSequenceIdFactory;
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
    private static final long ID = 1654529297522622464L; // "20200101:123456.789'0"
    private static final TimeSequenceId TIME_SEQUENCE_ID = TimeSequenceIdFactory.timeSequenceIdFactory().fromLong(ID);

    @Test
    public void test001() {
        String result = TimeSequenceIdForPer.timeSequenceIdForPer().format(TIME_SEQUENCE_ID);
        assertThat(result, is("20200101:123456.789Z'0"));
    }

//    @Test
//    public void test002() {
//        String result = LongTimeSequenceIdFormatter.longTimeSequenceIdFormatter().format(TIME_SEQUENCE_ID.getTime(), TIME_SEQUENCE_ID.getSequence());
//        assertThat(result, is("20190506:213239.371Z'5"));
//    }

    @Test
    public void test003() {
        String id = "20190506:213239.371Z'5";

        TimeSequenceId result = TimeSequenceIdForPer.timeSequenceIdForPer().parse(id);
        assertThat(result.getTime(), is(TIME_LONG));
        assertThat(result.getSequence(), is(5L));
    }

    @Test
    public void test004() {
        TimeSequenceId id = TimeSequenceIdGeneratorFactory.timeSequenceIdGenerator().generate();

        String formattedString = TimeSequenceIdForPer.timeSequenceIdForPer().format(id);
        TimeSequenceId newId = TimeSequenceIdForPer.timeSequenceIdForPer().parse(formattedString);
        System.out.println("id=" + id + " newId=" + newId);
        assertThat(id, is(newId));
    }

    @Test
    public void test005() {
        TimeSequenceId timeSequenceId = TimeSequenceIdGeneratorFactory.timeSequenceIdGenerator().generate();

        String formattedString = TimeSequenceIdForPer.timeSequenceIdForPer().format(timeSequenceId);
        TimeSequenceId newTimeSequenceId = TimeSequenceIdForPer.timeSequenceIdForPer().parse(formattedString);
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
        TimeSequenceIdForPer.timeSequenceIdForPer().parse("20190506:213239.371_XXX");
    }

    @Test(expected = IllegalArgumentException.class)
    public void test008() {
        TimeSequenceIdForPer.timeSequenceIdForPer().parse("20190506XX:213239.371_5");
    }

    @Test(expected = IllegalArgumentException.class)
    public void test009() {
        TimeSequenceIdForPer.timeSequenceIdForPer().parse("20190506:213239.371");
    }

    @Test(expected = IllegalArgumentException.class)
    public void test010() {
        TimeSequenceIdForPer.timeSequenceIdForPer().parse("20200101:123456.789'0");
    }
}
