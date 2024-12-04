package okjava.util.id;

import com.google.common.collect.Lists;
import okjava.util.logger.LoggerUtils;
import org.junit.Test;
import org.slf4j.Logger;

import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.lessThan;

/**
 * @author Dmitry Babkin dpbabkin@gmail.com
 * 5/6/2019
 * 23:30.
 */
abstract class BaseIdGeneratorTest<V extends Comparable<V>> {

    private final Logger LOGGER = LoggerUtils.createLogger(this.getClass());

    abstract IdGenerator<V> getIdGenerator();

    @Test
    public void test001() {
        V result = getIdGenerator().generate();
        V result1 = getIdGenerator().generate();

        LOGGER.info(result.toString());
        LOGGER.info(result1.toString());
        assertThat(result, lessThan(result1));
    }


    private void test002native(int count) {

        List<V> results = Lists.newArrayList();

        for (int i = 0; i < count; i++) {
            V timeSequenceId = getIdGenerator().generate();
            LOGGER.info(timeSequenceId.toString());
            results.add(timeSequenceId);
        }

        for (int i = 1; i < results.size(); i++) {
            V timeSequenceId = results.get(i - 1);
            V timeSequenceId1 = results.get(i);
            assertThat(timeSequenceId, lessThan(timeSequenceId1));
        }
    }

    @Test
    public void test002() {
        test002native(100_000);
    }

    @Test
    public void test003() throws InterruptedException {

        List<Thread> threads = Lists.newArrayList();
        for (int i = 0; i < 100; i++) {
            Thread t = new Thread(() -> this.test002native(1_000));
            threads.add(t);
        }

        threads.forEach(Thread::start);

        for (Thread t : threads) {
            t.join(TimeUnit.MINUTES.toMillis(1L));
            if (t.isAlive()) {
                throw new IllegalStateException(t.toString());
            }
        }
    }

    @Test
    public void test004() throws InterruptedException {

        Set<V> set = ConcurrentHashMap.newKeySet();

        List<Thread> threads = Lists.newArrayList();
        for (int i = 0; i < 1_000; i++) {

            Thread t = new Thread(() -> {
                List<V> list = Lists.newArrayListWithExpectedSize(10_000);
                for (int j = 0; j < 1_000; j++) {
                    V timeSequenceId = getIdGenerator().generate();
                    list.add(timeSequenceId);
                }

                list.forEach(timeSequenceId -> {

                    //boolean contain = set.add(timeSequenceId);
                    //assertThat("" + timeSequenceId, contain, is(true));

                    boolean existsBefore = set.contains(timeSequenceId);
                    assertThat("existsBefore " + timeSequenceId, existsBefore, is(false));

                    boolean changed = set.add(timeSequenceId);
                    assertThat("changed " + timeSequenceId, changed, is(true));

                    boolean existsAfter = set.contains(timeSequenceId);
                    assertThat("existsAfter " + timeSequenceId, existsAfter, is(true));


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
