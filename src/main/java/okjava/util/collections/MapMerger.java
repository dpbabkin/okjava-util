package okjava.util.collections;

import okjava.util.Function3;

import java.util.Map;

/**
 * @author Dmitry Babkin dpbabkin@gmail.com
 * 6/24/2016
 * 19:41.
 */
interface MapMerger<K, V> extends Function3<Map<K, V>, K, V, Map<K, V>> {

    Map<K, V> merge(Map<K, V> map, K key, V value);

    @Override
    default Map<K, V> apply(Map<K, V> map, K key, V value) {
        return merge(map, key, value);
    }
}
