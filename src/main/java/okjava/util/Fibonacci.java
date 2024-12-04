package okjava.util;


import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.google.common.collect.ImmutableBiMap;

import static okjava.util.NotNull.notNull;

/**
 * @author Dmitry Babkin dpbabkin@gmail.com
 * 2/9/2015
 * 20:32.
 */
public final class Fibonacci {

    private final long maxRange;
    private final long maxFibonacci;

    private final BiMap<Integer, Long> table;

    public Fibonacci() {
        this(Long.MAX_VALUE);
    }

    public Fibonacci(long maxRange) {
        this.maxRange = maxRange;
        this.table = ImmutableBiMap.copyOf(generate(maxRange));
        this.maxFibonacci = table.get(1 + table.size());
    }

    private static BiMap<Integer, Long> generate(long maxValue) {
        BiMap<Integer, Long> result = HashBiMap.create();

        long a = 1L;
        long b = 1L;

        result.put(2, 1L);
        int index = 3;
        for (; ; ) {
            b = a + b;
            if (b < 0 || b > maxValue) break;
            a = b - a;
            result.put(index++, b);
        }
        return result;
    }

    public long getMaxFibonacci() {
        return maxFibonacci;
    }

    private BiMap<Integer, Long> getTable() {
        return table;
    }

    public long getMaxRange() {
        return maxRange;
    }


    public long get(int number) {
        return switch (number) {
            case 0 -> 0L;
            case 1 -> 1L;
            case 2 -> 1L;
            default -> notNull(table.get(number));
        };
    }

    public long getNext(long number) {
        if (number >= maxFibonacci || number <= 0) {
            throw new IllegalArgumentException("out of range: " + number);
        }
        return notNull(table.get(getIndexAndCheck(number) + 1));
    }

    public long getPrev(long number) {
        if (number < 1L) {
            throw new IllegalArgumentException("out of range: " + number);
        }
        if (number == 1L) {
            return 1L;
        }
        return table.get(getIndexAndCheck(number) - 1);
    }

    private Integer getIndexAndCheck(long number) {
        if (number > maxFibonacci | number < 1) {
            throw new IllegalArgumentException("out of range: " + number);
        }
        Integer index = table.inverse().get(number);
        if (index == null) {
            throw new IllegalArgumentException("is not a fibonacci number: " + number);
        }
        return index;
    }
}
