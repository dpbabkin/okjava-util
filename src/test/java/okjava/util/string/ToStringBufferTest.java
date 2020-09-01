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
        assertThat(result, is("test { RuntimeException=java.lang.RuntimeException { message=msg0 thread=main } }"));
    }

    @Test
    public void testRuntimeExceptionNoMessage() {
        String result = ToStringBuffer.string("test").addThrowable(new RuntimeException()).toString();
        assertThat(result, is("test { RuntimeException=java.lang.RuntimeException { thread=main } }"));
    }

    @Test
    public void testIllegalArgumentException() {
        String result = ToStringBuffer.string("test").addThrowable(new IllegalArgumentException("msg1")).toString();
        assertThat(result, is("test { RuntimeException=java.lang.IllegalArgumentException { message=msg1 thread=main } }"));
    }

    @Test
    public void testError() {
        String result = ToStringBuffer.string("test").addThrowable(new Error("msg2")).toString();
        assertThat(result, is("test { Error=java.lang.Error { message=msg2 thread=main } }"));
    }

    @Test
    public void testAssertionError() {
        String result = ToStringBuffer.string("test").addThrowable(new AssertionError("msg3")).toString();
        assertThat(result, is("test { Error=java.lang.AssertionError { message=msg3 thread=main } }"));
    }

    @Test
    public void testException() {
        String result = ToStringBuffer.string("test").addThrowable(new Exception("msg4")).toString();
        assertThat(result, is("test { Exception=java.lang.Exception { message=msg4 thread=main } }"));
    }

    @Test
    public void testIOException() {
        String result = ToStringBuffer.string("test").addThrowable(new IOException("msg5")).toString();
        assertThat(result, is("test { Exception=java.io.IOException { message=msg5 thread=main } }"));
    }

    @Test
    public void testThrowable() {
        String result = ToStringBuffer.string("test").addThrowable(new Throwable("msg6")).toString();
        assertThat(result, is("test { Throwable=java.lang.Throwable { message=msg6 thread=main } }"));
    }

    @Test
    public void testNoneThrowable() {
        String result = ToStringBuffer.string("test").addThrowable(new NoneThrowable("msg7")).toString();
        assertThat(result, is("test { Throwable=okjava.util.string.ToStringBufferTest$NoneThrowable { message=msg7 thread=main } }"));
    }

    static class NoneThrowable extends Throwable {
        private NoneThrowable(String message) {
            super(message);
        }
    }


}
