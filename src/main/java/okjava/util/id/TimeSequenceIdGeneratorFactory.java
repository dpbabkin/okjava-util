package okjava.util.id;

import static okjava.util.check.Never.neverNeverCalled;

import okjava.util.SupplierUtils;
import okjava.util.annotation.Utility;
import okjava.util.check.Never;
import okjava.util.id.timesequence.LongTimeSequenceIdUtils;

import java.util.function.Function;
import java.util.function.Supplier;

/**
 * @author Dmitry Babkin dpbabkin@gmail.com
 * 5/7/2019
 * 23:12.
 */
@Utility
public enum TimeSequenceIdGeneratorFactory {
    ;

    TimeSequenceIdGeneratorFactory(@SuppressWarnings("unused") Never never) {
        neverNeverCalled();
    }

    private static final IdGenerator<Long> INSTANCE = LongTimeTimeSequenceIdGenerator.i();

    public static IdGenerator<Long> i() {
        return INSTANCE;
    }

    public static IdGenerator<String> withStringPrefixAndFormattedId(String prefix) {
        Function<Long, String> mapper = id -> prefix + LongTimeSequenceIdUtils.formatId(id);
        return withMapper(mapper);
    }

    public static <F> IdGenerator<F> withMapper(Function<Long, F> mapper) {
        Supplier<F> supplier = SupplierUtils.map(timeSequenceIdGenerator(), mapper);
        return supplier::get;
    }

    public static IdGenerator<Long> timeSequenceIdGenerator() {
        return INSTANCE;
    }

}
