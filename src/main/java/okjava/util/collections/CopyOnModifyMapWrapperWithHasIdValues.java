package okjava.util.collections;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import okjava.util.has.HasId;

import java.util.EnumMap;
import java.util.Map;

/**
 * @author Dmitry Babkin dpbabkin@gmail.com
 * 6/6/2016
 * 22:24.
 */
public final class CopyOnModifyMapWrapperWithHasIdValues<K, V extends HasId<K>> extends BaseCopyOnModifyMapWrapper<K, V> {

    private CopyOnModifyMapWrapperWithHasIdValues(Map<K, V> map, MapMerger<K, V> mapMerger) {
        super(map, mapMerger);
    }

    public static <K, V extends HasId<K>> CopyOnModifyMapWrapperWithHasIdValues<K, V> create() {
        return createCopyOnModifyMapWrapperWithHasIdValues();
    }

    public static <K, V extends HasId<K>> CopyOnModifyMapWrapperWithHasIdValues<K, V> createCopyOnModifyMapWrapperWithHasIdValues() {
        return new CopyOnModifyMapWrapperWithHasIdValues<>(ImmutableMap.of(), MapUtils::merge);
    }

    public static <K extends Enum<K>, V extends HasId<K>> CopyOnModifyMapWrapperWithHasIdValues<K, V> createEnum(Class<K> clazz) {
        return new CopyOnModifyMapWrapperWithHasIdValues<>(Maps.immutableEnumMap(new EnumMap<>(clazz)), MapUtils::mergeEnum);
    }

    public <VV extends V> VV put(VV value) {
        return put(value, HasId::getId);
    }
}
