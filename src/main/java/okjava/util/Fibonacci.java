package okjava.util;


import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.google.common.collect.ImmutableBiMap;

/**
 * @author Dmitry Babkin dpbabkin@gmail.com
 * 2/9/2015
 * 20:32.
 */
public final class Fibonacci {

    private final int maxValue;
    private final int maxFibonache;
    private final BiMap<Integer, Integer> table;

    public Fibonacci() {
        this(Integer.MAX_VALUE);
    }

    public Fibonacci(int maxValue) {
        this.maxValue = maxValue;
        this.table = ImmutableBiMap.copyOf(generate(maxValue));
        this.maxFibonache = table.get(table.size() + 1);
    }

    private static BiMap<Integer, Integer> generate(int maxValue) {
        BiMap<Integer, Integer> result = HashBiMap.create();

        int a = 1;
        int b = 1;

        result.put(2, 1);
        int index = 3;
        for (; ; ) {
            b = a + b;
            if (b < 0 || b > maxValue) break;
            a = b - a;
            result.put(index++, b);
        }
        return result;
    }

    public int getMaxFibonache() {
        return maxFibonache;
    }

    public BiMap<Integer, Integer> getTable() {
        return table;
    }

    public int getMaxValue() {
        return maxValue;
    }

    public int getNext(int number) {
        if (number == maxFibonache) {
            throw new IllegalArgumentException("out of range: " + number);
        }
        return table.get(getIndexAndCheck(number) + 1);
    }

    public int getPrev(int number) {
        if (number == 1) {
            return 1;
        }
        return table.get(getIndexAndCheck(number) - 1);
    }

    private Integer getIndexAndCheck(int number) {
        if (number > maxFibonache | number < 1) {
            throw new IllegalArgumentException("out of range: " + number);
        }
        Integer index = table.inverse().get(number);
        if (index == null) {
            throw new IllegalArgumentException("is not a fibonachi number: " + number);
        }
        return index;
    }
}
