package okjava.util.thread;


import org.junit.Test;

/**
 * @author Dmitry Babkin dpbabkin@gmail.com
 * 13.01.2022 - 21:37.
 */
public class SnailTest {

    @Test
    public void tesSnail0() {
        System.out.println(Snail.getSnail0());
    }

    @Test
    public void tesSnail1() {
        System.out.println(Snail.getSnail1());
    }

    @Test
    public void tesSnail2() {
        System.out.println(Snail.getSnail2());
    }


    @Test
    public void tesStop() {
        System.out.println(Snail.stop());
    }
}
