package okjava.util.id;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import com.google.common.collect.Lists;

import okjava.util.id.timesequence.TimeSequenceId;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Dmitry Babkin dpbabkin@gmail.com
 * 5/6/2019
 * 23:30.
 */
abstract class BaseIdGeneratorTest {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    abstract IdGenerator<TimeSequenceId> getIdGenerator();

    @Test
    public void test001() {
        TimeSequenceId result = getIdGenerator().generate();
        TimeSequenceId result1 = getIdGenerator().generate();

        LOGGER.info(result.toString());
        LOGGER.info(result1.toString());
        assertThat(result.compareTo(result1), is(-1));
    }

    @Test
    public void test002() {

        List<TimeSequenceId> results = Lists.newArrayList();

        for (int i = 0; i < 10_000; i++) {
            TimeSequenceId timeSequenceId = getIdGenerator().generate();
            LOGGER.info(timeSequenceId.toString());
            results.add(timeSequenceId);
        }

        for (int i = 1; i < results.size(); i++) {
            TimeSequenceId timeSequenceId = results.get(i - 1);
            TimeSequenceId timeSequenceId1 = results.get(i);
            assertThat(timeSequenceId.compareTo(timeSequenceId1), is(-1));
        }
    }

    @Test
    public void test003() throws InterruptedException {

        List<Thread> threads = Lists.newArrayList();
        for (int i = 0; i < 100; i++) {
            Thread t = new Thread(this::test002);
            t.start();
            threads.add(t);
        }

        for (Thread t : threads) {
            t.join();
        }
    }


    @Test
    public void test004() throws InterruptedException {

        Set<TimeSequenceId> set = ConcurrentHashMap.newKeySet();

        List<Thread> threads = Lists.newArrayList();
        for (int i = 0; i < 1_000; i++) {

            Thread t = new Thread(() -> {
                List<TimeSequenceId> list = Lists.newArrayListWithExpectedSize(10_000);
                for (int j = 0; j < 1_000; j++) {
                    TimeSequenceId timeSequenceId = getIdGenerator().generate();
                    list.add(timeSequenceId);
                }

                list.forEach(timeSequenceId -> {
                    boolean contain = set.add(timeSequenceId);
                    assertThat("" + timeSequenceId, contain, is(true));
                });
            });
            t.start();
            threads.add(t);
        }

        for (Thread t : threads) {
            t.join();
        }
        assertThat(set.size(), is(1_000 * 1_000));
    }
}
