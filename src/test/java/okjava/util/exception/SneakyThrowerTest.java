package okjava.util.exception;

import org.junit.Test;

import java.io.IOException;

/**
 * @author Dmitry Babkin dpbabkin@gmail.com
 * 5/27/2019
 * 19:09.
 */
public class SneakyThrowerTest {

    @Test(expected = IOException.class)
    public void test001() {
        SneakyThrower.sneakyThrow(new IOException("sneaky"));
    }
}
