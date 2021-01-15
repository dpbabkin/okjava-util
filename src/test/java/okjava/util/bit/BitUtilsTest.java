package okjava.util.bit;


import okjava.util.datetime.DateTimeFormat;
import org.junit.Test;

import java.util.Random;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.core.Is.is;

/**
 * @author Dmitry Babkin dpbabkin@gmail.com
 * 5/7/2019
 * 18:36.
 */
public class BitUtilsTest {
    Random random = new Random();

    @Test
    public void test002() {

        long time = System.currentTimeMillis();

        System.out.println(Long.toBinaryString(time));
        System.out.println(Long.toBinaryString(time).length());
        System.out.println(BitUtils.rightBitCount(time));

        long v = (1L << 42) - 1;

        System.out.println(v);
        System.out.println(Long.toBinaryString(v));
        System.out.println(Long.toBinaryString(v).length());
        System.out.println(DateTimeFormat.create().longTimeToString(v));

        long c = 1L << 20;
        c -= 1;
        System.out.println(c);
        System.out.println(Long.toBinaryString(c));
        System.out.println(Long.toBinaryString(c).length());

        System.out.println(Long.toBinaryString(-23631L >> 5));
        System.out.println(Long.toBinaryString(-236341L >>> 5));
        System.out.println(Long.toBinaryString((1 << 20) - 1));
        System.out.println((1 << 20) - 1);
        System.out.println(1L << 42);


        System.out.println(Long.toBinaryString(3L << 62));
        System.out.println(Long.toBinaryString(3L << 62).length());
    }

    @Test
    public void testRightBitCount001() {
        int result = BitUtils.rightBitCount01(0);
        assertThat(result, is(0));

        result = BitUtils.rightBitCount01(1);
        assertThat(result, is(1));

        result = BitUtils.rightBitCount01(2);
        assertThat(result, is(2));

        result = BitUtils.rightBitCount01(3);
        assertThat(result, is(2));

        result = BitUtils.rightBitCount01(4);
        assertThat(result, is(3));

        result = BitUtils.rightBitCount01(5);
        assertThat(result, is(3));

        result = BitUtils.rightBitCount01(9);
        assertThat(result, is(4));
    }

    @Test
    public void testRightBitCount002() {
        for (long i = -10; i < 100_000L; i++) {
            int result01 = BitUtils.rightBitCount01(i);
            int result02 = BitUtils.rightBitCount02(i);
            int result = BitUtils.rightBitCount(i);
            assertThat(result, is(result01));
            assertThat(result, is(result02));
        }
    }

    @Test
    public void testRightBitCount004() {
        for (long i = 1; i < 5_000_000L; i++) {

            long n = random.nextLong();
            if (n < 0) n *= -1;
            int result = BitUtils.rightBitCount01(n);
            assertThat(result, greaterThan(0));
        }
    }

    @Test
    public void testRightBitCount003() {

        for (long i = 0; i < 1000_000L; i++) {

            long n = random.nextLong();
            if (n < 0) n *= -1;

            int result = BitUtils.rightBitCount(n);
            int result01 = BitUtils.rightBitCount01(n);
            int result02 = BitUtils.rightBitCount02(n);

            assertThat(result + " " + result01, result, is(result01));
            assertThat(result + " " + result02, result, is(result02));
        }
    }

    @Test
    public void testRightBitCount01() {
        int result = BitUtils.rightBitCount(0);
        assertThat(result, is(0));

        result = BitUtils.rightBitCount(1);
        assertThat(result, is(1));

        result = BitUtils.rightBitCount(2);
        assertThat(result, is(2));

        result = BitUtils.rightBitCount(3);
        assertThat(result, is(2));

        result = BitUtils.rightBitCount(4);
        assertThat(result, is(3));

        result = BitUtils.rightBitCount(5);
        assertThat(result, is(3));

        result = BitUtils.rightBitCount(9);
        assertThat(result, is(4));
    }

    @Test
    public void testRightBitCount02() {
        long result = Long.lowestOneBit(0L);
        assertThat(result, is(0L));

        result = Long.lowestOneBit(5L);
        assertThat(result, is(1L));

        result = Long.lowestOneBit(258L);
        assertThat(result, is(2L));

        result = Long.lowestOneBit(-1L);
        assertThat(result, is(1L));
    }

    @Test
    public void testRightBitCount03() {
        long result = Long.highestOneBit(0L);
        assertThat(result, is(0L));

        result = Long.highestOneBit(5L);
        assertThat(result, is(4L));

        result = Long.highestOneBit(258L);
        assertThat(result, is(256L));

        result = Long.highestOneBit(-1L);
        assertThat(result, is(Long.MIN_VALUE));
    }
}
