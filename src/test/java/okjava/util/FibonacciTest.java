package okjava.util;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Dmitry Babkin dpbabkin@gmail.com
 * 2/9/2015
 * 21:29.
 */
public class FibonacciTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(FibonacciTest.class);

    @Test
    public void test01() {
        Fibonacci f = new Fibonacci();

        for (int n = 2; n < f.getMaxFibonache(); n = f.getNext(n)) {
            LOGGER.info("n=" + n + " ,pref=" + f.getPrev(n) + " ,next=" + f.getNext(n) + " ddd=" + ((double) f.getNext(n) / n));
        }
    }
}
