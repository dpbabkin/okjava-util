package okjava.util;

import static okjava.util.check.Never.neverNeverCalled;

import com.google.common.collect.ImmutableBiMap;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.ImmutableSortedSet;

import okjava.util.annotation.Utility;
import okjava.util.check.Never;

import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;


@Utility
public enum GuavaCollectors {
    ;

    GuavaCollectors(@SuppressWarnings("unused") Never never) {
        neverNeverCalled();
    }


    public static <T> Collector<T, ?, ImmutableList<T>> toImmutableList() {
        Supplier<ImmutableList.Builder<T>> supplier = ImmutableList.Builder::new;
        BiConsumer<ImmutableList.Builder<T>, T> accumulator = ImmutableList.Builder::add;
        BinaryOperator<ImmutableList.Builder<T>> combiner = (l, r) -> l.addAll(r.build());
        Function<ImmutableList.Builder<T>, ImmutableList<T>> finisher = ImmutableList.Builder::build;
        return Collector.of(supplier, accumulator, combiner, finisher);
    }


    public static <T> Collector<T, ?, ImmutableSet<T>> toImmutableSet() {
        Supplier<ImmutableSet.Builder<T>> supplier = ImmutableSet.Builder::new;
        BiConsumer<ImmutableSet.Builder<T>, T> accumulator = ImmutableSet.Builder::add;
        BinaryOperator<ImmutableSet.Builder<T>> combiner = (l, r) -> l.addAll(r.build());
        Function<ImmutableSet.Builder<T>, ImmutableSet<T>> finisher = ImmutableSet.Builder::build;
        return Collector.of(supplier, accumulator, combiner, finisher, Collector.Characteristics.UNORDERED);
    }


    public static <T extends Comparable<?>> Collector<T, ?, ImmutableSortedSet<T>> toImmutableSortedSet() {
        Supplier<ImmutableSortedSet.Builder<T>> supplier = ImmutableSortedSet::naturalOrder;
        BiConsumer<ImmutableSortedSet.Builder<T>, T> accumulator = ImmutableSortedSet.Builder::add;
        BinaryOperator<ImmutableSortedSet.Builder<T>> combiner = (l, r) -> l.addAll(r.build());
        Function<ImmutableSortedSet.Builder<T>, ImmutableSortedSet<T>> finisher = ImmutableSortedSet.Builder::build;
        return Collector.of(supplier, accumulator, combiner, finisher, Collector.Characteristics.UNORDERED);
    }


    public static <T, K, V> Collector<T, ?, ImmutableMap<K, V>> toImmutableMap(Function<? super T, ? extends K> keyMapper, Function<? super T, ? extends V> valueMapper) {
        BiConsumer<ImmutableMap.Builder<K, V>, T> accumulator = (b, t) -> b.put(keyMapper.apply(t), valueMapper.apply(t));
        return toImmutableMap(accumulator);
    }

    public static <T, K, V> Collector<T, ?, ImmutableMap<K, V>> toImmutableMap(Function<? super T, Map.Entry<? extends K, ? extends V>> entryMapper) {
        BiConsumer<ImmutableMap.Builder<K, V>, T> accumulator = (b, t) -> {
            Map.Entry<? extends K, ? extends V> entry = entryMapper.apply(t);
            b.put(entry.getKey(), entry.getValue());
        };
        return toImmutableMap(accumulator);
    }

    private static <T, K, V> Collector<T, ?, ImmutableMap<K, V>> toImmutableMap(BiConsumer<ImmutableMap.Builder<K, V>, T> accumulator) {
        Supplier<ImmutableMap.Builder<K, V>> supplier = ImmutableMap.Builder::new;
        BinaryOperator<ImmutableMap.Builder<K, V>> combiner = (l, r) -> l.putAll(r.build());
        Function<ImmutableMap.Builder<K, V>, ImmutableMap<K, V>> finisher = ImmutableMap.Builder::build;
        return Collector.of(supplier, accumulator, combiner, finisher, Collector.Characteristics.UNORDERED);
    }


    public static <T, K, V> Collector<T, ?, ImmutableBiMap<K, V>> toImmutableBiMap(Function<? super T, ? extends K> keyMapper, Function<? super T, ? extends V> valueMapper) {
        BiConsumer<ImmutableBiMap.Builder<K, V>, T> accumulator = (b, t) -> b.put(keyMapper.apply(t), valueMapper.apply(t));
        return toImmutableBiMap(accumulator);
    }

    public static <T, K, V> Collector<T, ?, ImmutableBiMap<K, V>> toImmutableBiMap(Function<? super T, Map.Entry<? extends K, ? extends V>> entryMapper) {
        BiConsumer<ImmutableBiMap.Builder<K, V>, T> accumulator = (b, t) -> {
            Map.Entry<? extends K, ? extends V> entry = entryMapper.apply(t);
            b.put(entry.getKey(), entry.getValue());
        };
        return toImmutableBiMap(accumulator);
    }

    private static <T, K, V> Collector<T, ?, ImmutableBiMap<K, V>> toImmutableBiMap(BiConsumer<ImmutableBiMap.Builder<K, V>, T> accumulator) {
        Supplier<ImmutableBiMap.Builder<K, V>> supplier = ImmutableBiMap.Builder::new;
        BinaryOperator<ImmutableBiMap.Builder<K, V>> combiner = (l, r) -> l.putAll(r.build());
        Function<ImmutableBiMap.Builder<K, V>, ImmutableBiMap<K, V>> finisher = ImmutableBiMap.Builder::build;
        return Collector.of(supplier, accumulator, combiner, finisher, Collector.Characteristics.UNORDERED);
    }
}