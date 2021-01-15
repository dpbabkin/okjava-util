package okjava.util.collections;

import okjava.util.Final;

import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;

import static okjava.util.Final.create;
import static okjava.util.NotNull.notNull;

/**
 * @author Dmitry Babkin dpbabkin@gmail.com
 * 6/6/2016
 * 22:24.
 */
abstract class BaseCopyOnModifyMapWrapper<K, V> implements Supplier<Map<K, V>> {

    private final Object mutex = new Object();
    private final MapMerger<K, V> mapMerger;

    private Final<Map<K, V>> map;

    BaseCopyOnModifyMapWrapper(Map<K, V> map, MapMerger<K, V> mapMerger) {
        this.map = create(map);
        this.mapMerger = notNull(mapMerger);
    }

    @Override
    public Map<K, V> get() { //'map' is not protected by lock in this call. I'm fine with that.
        return map.get();
    }

    @SuppressWarnings("unchecked") //save since covariant.
    private <VV extends V> VV get(K key) {
        return (VV) map.get().get(key);
    }

    final <VV extends V> VV put(VV value, Function<V, K> idResolver) {
        return put(idResolver.apply(value), value);
    }

    public <VV extends V> VV put(K key, VV value) {
        synchronized (mutex) {
            V v = map.get().get(key);
            if (v != null) {
                throw new IllegalStateException("key " + key + " already exists, value= " + v);
            }
            map = create(mapMerger.merge(map.get(), key, value));
        }
        return value;
    }

    public <VV extends V> VV getOrCreate(K key, Function<K, VV> creator) {
        VV value = get(key);
        if (value == null) {
            synchronized (mutex) {
                value = get(key);
                if (value == null) {
                    value = notNull(creator.apply(key));
                    map = create(mapMerger.merge(map.get(), key, value));
                }
            }
        }
        return value;
    }
}
