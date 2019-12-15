package okjava.util;

import org.junit.Test;

public class SomeTest {

    //@Test
    public void test001() {
        for (int i = 0; ; i++) {
            if (i < 0) break;
            if (System.currentTimeMillis() * i == 0) {
                System.out.println("UPS!");
            }
        }
    }
}
