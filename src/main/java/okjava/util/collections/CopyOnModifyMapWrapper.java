package okjava.util.collections;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;

import java.util.EnumMap;
import java.util.Map;

/**
 * @author Dmitry Babkin dpbabkin@gmail.com
 *         6/6/2016
 *         22:24.
 */
public final class CopyOnModifyMapWrapper<K, V> extends BaseCopyOnModifyMapWrapper<K, V> {

    private CopyOnModifyMapWrapper(Map<K, V> map, MapMerger<K, V> mapMerger) {
        super(map, mapMerger);
    }

    public static <K, V> CopyOnModifyMapWrapper<K, V> create() {
        return createCopyOnModifyMapWrapper();
    }

    public static <K, V> CopyOnModifyMapWrapper<K, V> createCopyOnModifyMapWrapper() {
        return new CopyOnModifyMapWrapper<>(ImmutableMap.of(), MapUtils::merge);
    }


    public static <K extends Enum<K>, V> CopyOnModifyMapWrapper<K, V> createEnum(Class<K> clazz) {
        return new CopyOnModifyMapWrapper<>(Maps.immutableEnumMap(new EnumMap<>(clazz)), MapUtils::mergeEnum);
    }
}
