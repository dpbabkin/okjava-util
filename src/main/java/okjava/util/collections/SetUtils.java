package okjava.util.collections;

import com.google.common.collect.ImmutableSet;

import okjava.util.annotation.Utility;
import okjava.util.check.Never;

import java.util.Set;

/**
 * @author Dmitry Babkin dpbabkin@gmail.com
 *         6/6/2016
 *         22:01.
 */
@Utility
public final class SetUtils {

    private SetUtils(Never never) {
        Never.neverCalled();
    }

    public static <V> Set<V> merge(Set<V> sourceSet, V value) {
        ImmutableSet.Builder<V> builder = createBuilderFromSet(sourceSet);
        builder.add(value);
        return builder.build();
    }

    public static <V> Set<V> merge(Set<V> set1, Set<V> set2) {
        ImmutableSet.Builder<V> builder = createBuilderFromSet(set1);
        builder.addAll(set2);
        return builder.build();
    }

    private static <V> ImmutableSet.Builder<V> createBuilderFromSet(Set<V> set) {
        ImmutableSet.Builder<V> builder = ImmutableSet.builder();
        builder.addAll(set);
        return builder;
    }
}
