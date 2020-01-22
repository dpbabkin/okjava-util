package okjava.util.id;

import okjava.util.SupplierUtils;
import okjava.util.annotation.Utility;
import okjava.util.check.Never;
import okjava.util.id.timesequence.TimeSequenceIdFactory;

import java.util.function.Function;
import java.util.function.Supplier;

import static okjava.util.check.Never.neverNeverCalled;
import static okjava.util.id.format.TimeSequenceIdFormatter.timeSequenceIdFormatter;

/**
 * @author Dmitry Babkin dpbabkin@gmail.com
 * 5/7/2019
 * 23:12.
 */
@Utility
public enum TimeSequenceIdGeneratorFactory {
    ;

    private static final IdGenerator<LongTimeSequenceId> TIME_SEQUENCE_ID_ID_GENERATOR = withMapper(l -> TimeSequenceIdFactory.timeSequenceIdFactory().fromLong(l));

    TimeSequenceIdGeneratorFactory(@SuppressWarnings("unused") Never never) {
        neverNeverCalled();
    }

    public static IdGenerator<String> withStringPrefixAndFormattedId(String prefix) {
        Function<Long, String> mapper = id -> prefix + timeSequenceIdFormatter().format(id);
        return withMapper(mapper);
    }

    public static IdGenerator<String> withFormattedId() {
        Function<Long, String> mapper = timeSequenceIdFormatter().getFormatter();
        return withMapper(mapper);
    }

    public static <F> IdGenerator<F> withMapper(Function<Long, F> mapper) {
        Supplier<F> supplier = SupplierUtils.map(longTimeSequenceIdGenerator(), mapper);
        return supplier::get;
    }

    public static IdGenerator<Long> longTimeSequenceIdGenerator() {
        return LongTimeTimeSequenceIdGenerator.timeSequenceIdGenerator();
    }


    public static IdGenerator<LongTimeSequenceId> timeSequenceIdGenerator() {
        return TIME_SEQUENCE_ID_ID_GENERATOR;
    }
}
