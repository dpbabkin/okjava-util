package okjava.util;

import okjava.util.logger.LoggerUtils;
import org.junit.Test;
import org.slf4j.Logger;

/**
 * @author Dmitry Babkin dpbabkin@gmail.com
 * 2/9/2015
 * 21:29.
 */
public class FibonacciTest {

    private static final Logger LOGGER = LoggerUtils.createLogger(FibonacciTest.class);

    @Test
    public void test01() {
        Fibonacci fibonacci = new Fibonacci();
        LOGGER.info("getMaxFibonacci = " + fibonacci.getMaxFibonacci());
        LOGGER.info("getMaxRange = " + fibonacci.getMaxRange());
        long n = 2;
        for (; ; ) {
            long pref = fibonacci.getPrev(n);
            LOGGER.info("n=" + n + " ,pref=" + pref + " ratio=" + ((double) n / pref));
            if (n >= fibonacci.getMaxFibonacci()) {
                break;
            }
            n = fibonacci.getNext(n);
        }
    }
}
