package okjava.util.string;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

import org.junit.Test;

/**
 * @author Dmitry Babkin dpbabkin@gmail.com
 * 5/3/2019
 * 19:27.
 */

public class ToStringBufferTest {

    @Test
    public void test001(){
        String result=ToStringBuffer.create("someClass").add("s",2).add("fdf",432423).toString();
        assertThat(result,is("someClass{ s=2 fdf=432423 }"));
    }
}
