package okjava.util;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.ImmutableSortedSet;
import com.google.common.collect.Ordering;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static com.google.common.collect.ImmutableList.toImmutableList;
import static com.google.common.collect.ImmutableMap.toImmutableMap;
import static com.google.common.collect.ImmutableSet.toImmutableSet;

import static com.google.common.collect.ImmutableSortedSet.toImmutableSortedSet;
import static java.util.Collections.emptyList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * @author Dmitry Babkin dpbabkin@gmail.com
 * 6/1/2016
 * 00:53.
 */
public class GuavaCollectorsTest {



    static {
        //String name = "Joan";
        String info = "test ";
        System.out.println(info);

    }
    @Test
    public void testToImmutableSortedSet_Sort() {
        List<String> values = Arrays.asList("charlie", "bravo", "delta", "india", "echo", "alpha");
        ImmutableSet<String> res = values.stream().collect(toImmutableSortedSet(Ordering.natural()));
        assertEquals("[alpha, bravo, charlie, delta, echo, india]", res.toString());
    }

    @Test
    public void testToImmutableSortedSet_Parallel() {
        List<String> values = Arrays.asList("charlie", "bravo", "delta", "india", "echo", "alpha");
        ImmutableSet<String> res = values.parallelStream().collect(toImmutableSortedSet(Ordering.natural()));
        assertEquals("[alpha, bravo, charlie, delta, echo, india]", res.toString());
    }

    @Test
    public void testToImmutableSortedSet_Empty() {
        List<String> values = emptyList();
        ImmutableSortedSet<String> res = values.stream().collect(toImmutableSortedSet(Ordering.natural()));
        assertTrue(res.isEmpty());
    }

    @Test
    public void testToImmutableSet_Sort() {
        List<Integer> values = Arrays.asList(5, 1, 22, 4, 8, 9, 3, 3, 989);
        ImmutableSet<Integer> res = values.stream().collect(toImmutableSet());
        assertEquals("[5, 1, 22, 4, 8, 9, 3, 989]", res.toString());
    }

    @Test
    public void testToImmutableSet_Parallel() {
        List<Integer> values = Arrays.asList(5, 1, 22, 4, 8, 9, 3, 3, 989);
        ImmutableSet<Integer> res = values.parallelStream().collect(toImmutableSet());
        assertEquals("[5, 1, 22, 4, 8, 9, 3, 989]", res.toString());
    }

    @Test
    public void testToImmutableSet_Empty() {
        List<String> values = emptyList();
        ImmutableSet<String> res = values.stream().collect(toImmutableSet());
        assertTrue(res.isEmpty());
    }

    @Test
    public void testToImmutableList_Sort() {
        List<Integer> values = Arrays.asList(1, 3, 4, 5, 8, 9, 3, 989);
        ImmutableList<Integer> res = values.stream().collect(toImmutableList());
        assertEquals("[1, 3, 4, 5, 8, 9, 3, 989]", res.toString());
    }

    @Test
    public void testToImmutableList_Parallel() {
        List<Integer> values = Arrays.asList(1, 3, 4, 5, 8, 9, 3, 989);
        ImmutableList<Integer> res = values.parallelStream().collect(toImmutableList());
        assertEquals("[1, 3, 4, 5, 8, 9, 3, 989]", res.toString());
    }

    @Test
    public void testToImmutableMap_Sort() {
        List<Data> values = Arrays.asList(new Data(22, "a"), new Data(11, "bb"), new Data(231, "cc"), new Data(1231, "cc"));
        ImmutableMap<Integer, String> res = values.stream().collect(toImmutableMap(d -> d.i, d -> d.s));
        assertEquals("{22=a, 11=bb, 231=cc, 1231=cc}", res.toString());
    }

    @Test
    public void testToImmutableMap_Empty() {
        List<Data> values = emptyList();
        ImmutableMap<Integer, String> res = values.stream().collect(toImmutableMap(d -> d.i, d -> d.s));
        assertTrue(res.isEmpty());
    }

    private static class Data {
        private final Integer i;
        private final String s;

        Data(Integer i, String s) {
            this.i = i;
            this.s = s;
        }
    }
}
