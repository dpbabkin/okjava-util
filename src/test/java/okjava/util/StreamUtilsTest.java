package okjava.util;

import static com.google.common.collect.Lists.newArrayList;
import static java.util.stream.Collectors.toSet;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.IsCollectionContaining.hasItems;

import org.junit.Test;

import java.util.Set;

/**
 * @author Dmitry Babkin dpbabkin@gmail.com
 * 6/5/2016
 * 00:23.
 */
public class StreamUtilsTest {

    @Test
    public void test() {
        Iterable<String> iterable = newArrayList("A", "B", "C");

        Set<String> set = StreamUtils.toStream(iterable).collect(toSet());
        assertThat(set, hasSize(3));
        assertThat(set, hasItems("A", "B", "C"));
    }
}
