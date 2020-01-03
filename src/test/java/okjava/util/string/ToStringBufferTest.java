package okjava.util.string;

import org.junit.Test;

import java.io.IOException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

/**
 * @author Dmitry Babkin dpbabkin@gmail.com
 * 5/3/2019
 * 19:27.
 */

public class ToStringBufferTest {

    @Test
    public void test001() {
        String result = ToStringBuffer.string("someClass").add("s", 2).add("fdf", 432423).toString();
        assertThat(result, is("someClass { s=2 fdf=432423 }"));
    }

    @Test
    public void testRuntimeException() {
        String result = ToStringBuffer.string("test").addThrowable(new RuntimeException("msg0")).toString();
        assertThat(result, is("test { java.lang.RuntimeException=msg0 { class=java.lang.RuntimeException } }"));
    }

    @Test
    public void testIllegalArgumentException() {
        String result = ToStringBuffer.string("test").addThrowable(new IllegalArgumentException("msg1")).toString();
        assertThat(result, is("test { java.lang.RuntimeException=msg1 { class=java.lang.IllegalArgumentException } }"));
    }

    @Test
    public void testError() {
        String result = ToStringBuffer.string("test").addThrowable(new Error("msg2")).toString();
        assertThat(result, is("test { java.lang.Error=msg2 { class=java.lang.Error } }"));
    }

    @Test
    public void testAssertionError() {
        String result = ToStringBuffer.string("test").addThrowable(new AssertionError("msg3")).toString();
        assertThat(result, is("test { java.lang.Error=msg3 { class=java.lang.AssertionError } }"));
    }

    @Test
    public void testException() {
        String result = ToStringBuffer.string("test").addThrowable(new Exception("msg4")).toString();
        assertThat(result, is("test { java.lang.Exception=msg4 { class=java.lang.Exception } }"));
    }

    @Test
    public void testIOException() {
        String result = ToStringBuffer.string("test").addThrowable(new IOException("msg5")).toString();
        assertThat(result, is("test { java.lang.Exception=msg5 { class=java.io.IOException } }"));
    }

    @Test
    public void testThrowable() {
        String result = ToStringBuffer.string("test").addThrowable(new Throwable("msg6")).toString();
        assertThat(result, is("test { java.lang.Throwable=msg6 { class=java.lang.Throwable } }"));
    }

    @Test
    public void testNoneThrowable() {
        String result = ToStringBuffer.string("test").addThrowable(new NoneThrowable("msg7")).toString();
        assertThat(result, is("test { java.lang.Throwable=msg7 { class=okjava.util.string.ToStringBufferTest$NoneThrowable } }"));
    }

    static class NoneThrowable extends Throwable {
        private NoneThrowable(String message) {
            super(message);
        }
    }


}
