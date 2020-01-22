package okjava.util.id;

import okjava.util.SupplierUtils;
import okjava.util.annotation.Utility;
import okjava.util.check.Never;
import okjava.util.id.timesequence.LongTimeSequenceIdUtils;
import okjava.util.id.timesequence.TimeSequenceId;
import okjava.util.id.timesequence.TimeSequenceIdFactory;

import java.util.function.Function;
import java.util.function.Supplier;

import static okjava.util.check.Never.neverNeverCalled;

/**
 * @author Dmitry Babkin dpbabkin@gmail.com
 * 5/7/2019
 * 23:12.
 */
@Utility
public enum TimeSequenceIdGeneratorFactory {
    ;

    //private static final IdGenerator<Long> INSTANCE = LongTimeTimeSequenceIdGenerator.i();

    TimeSequenceIdGeneratorFactory(@SuppressWarnings("unused") Never never) {
        neverNeverCalled();
    }

    public static IdGenerator<String> withStringPrefixAndFormattedId(String prefix) {
        Function<Long, String> mapper = id -> prefix + LongTimeSequenceIdUtils.format(id);
        return withMapper(mapper);
    }

    public static IdGenerator<String> withFormattedId() {
        Function<Long, String> mapper = LongTimeSequenceIdUtils::format;
        return withMapper(mapper);
    }

    public static <F> IdGenerator<F> withMapper(Function<Long, F> mapper) {
        Supplier<F> supplier = SupplierUtils.map(longTimeSequenceIdGenerator(), mapper);
        return supplier::get;
    }

    public static IdGenerator<Long> longTimeSequenceIdGenerator() {
        return LongTimeTimeSequenceIdGenerator.timeSequenceIdGenerator();
    }

    private static final IdGenerator<TimeSequenceId> TIME_SEQUENCE_ID_ID_GENERATOR = withMapper(l -> TimeSequenceIdFactory.timeSequenceIdFactory().fromLong(l));

    public static IdGenerator<TimeSequenceId> timeSequenceIdGenerator() {
        return TIME_SEQUENCE_ID_ID_GENERATOR;
    }
}
