package okjava.util.collections;

import static com.google.common.collect.Maps.newEnumMap;
import static com.google.common.collect.Maps.newHashMap;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;

import okjava.util.annotation.Utility;
import okjava.util.check.Never;

import java.util.EnumMap;
import java.util.Map;

/**
 * @author Dmitry Babkin dpbabkin@gmail.com
 *         6/6/2016
 *         22:01.
 */
@Utility
public final class MapsUtils {

    private MapsUtils(Never never) {
        Never.neverCalled();
    }

    public static <K, V> Map<K, V> merge(Map<K, V> sourceMap, K key, V value) {
        Map<K, V> builder = createBuilderFromMap(sourceMap);
        builder.put(key, value);
        return ImmutableMap.copyOf(builder);
    }

    public static <K, V> Map<K, V> merge(Map<K, V> map1, Map<K, V> map2) {
        Map<K, V> builder = createBuilderFromMap(map1);
        builder.putAll(map2);
        return ImmutableMap.copyOf(builder);
    }

    public static <K extends Enum<K>, V> Map<K, V> mergeEnum(Map<K, V> sourceMap, K key, V value) {
        EnumMap<K, V> builder = newEnumMap(key.getDeclaringClass());
        builder.putAll(sourceMap);
        builder.put(key, value);
        return Maps.immutableEnumMap(builder);
    }

    @SuppressWarnings("unchecked")
    private static <K, V> Map<K, V> createBuilderFromMap(Map<K, V> map) {
        if (map instanceof EnumMap) {
            return (Map<K, V>) newEnumMap((EnumMap<?, ?>) map);
        } else {
            return newHashMap(map);
        }
    }
}
