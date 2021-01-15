package okjava.util.id;

import okjava.util.SupplierUtils;
import okjava.util.annotation.Utility;
import okjava.util.check.Never;
import okjava.util.id.timesequence.TimeSequenceId;
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


    private static final IdGenerator<LongTimeSequenceId> LONG_TIME_SEQUENCE_ID_ID_GENERATOR = withMapper(id -> TimeSequenceIdFactory.timeSequenceIdFactory().fromLong(id));
    private static final IdGenerator<TimeSequenceId> TIME_SEQUENCE_ID_ID_GENERATOR = LONG_TIME_SEQUENCE_ID_ID_GENERATOR::generate;

    TimeSequenceIdGeneratorFactory(@SuppressWarnings("unused") Never never) {
        neverNeverCalled();
    }

    public static IdGenerator<String> withStringPrefixAndFormattedId(String prefix) {
        Function<Long, String> mapper = id -> prefix + timeSequenceIdFormatter().format(id);
        return withMapper(mapper);
    }

    private static final IdGenerator<String> STRING_TIME_SEQUENCE_ID_ID_GENERATOR = withMapper(timeSequenceIdFormatter().getFormatter());

    public static <F> IdGenerator<F> withMapper(Function<Long, F> mapper) {
        Supplier<F> supplier = SupplierUtils.map(longIdGenerator(), mapper);
        return supplier::get;
    }

    public static IdGenerator<String> stringIdGenerator() {
        return STRING_TIME_SEQUENCE_ID_ID_GENERATOR;
    }

    public static IdGenerator<Long> longIdGenerator() {
        return LongTimeTimeSequenceIdGenerator.timeSequenceIdGenerator();
    }

    public static IdGenerator<TimeSequenceId> timeSequenceIdGenerator() {
        return TIME_SEQUENCE_ID_ID_GENERATOR;
    }

    public static IdGenerator<LongTimeSequenceId> longTimeSequenceIdGenerator() {
        return LONG_TIME_SEQUENCE_ID_ID_GENERATOR;
    }
}
