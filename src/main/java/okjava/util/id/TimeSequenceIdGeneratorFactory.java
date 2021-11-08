package okjava.util.id;

import okjava.util.SupplierUtils;
import okjava.util.annotation.Utility;
import okjava.util.check.Never;
import okjava.util.id.timesequence.TimeSequenceId;

import java.util.function.Function;
import java.util.function.Supplier;

import static okjava.util.check.Never.neverNeverCalled;
import static okjava.util.id.format.TimeSequenceIdFoPaR.timeSequenceIdFoPaR;

/**
 * @author Dmitry Babkin dpbabkin@gmail.com
 * 5/7/2019
 * 23:12.
 */
@Utility
public enum TimeSequenceIdGeneratorFactory {
    ;

    // private static final IdGenerator<LongTimeSequenceId> LONG_TIME_SEQUENCE_ID_ID_GENERATOR = withMapper(id -> ((LongTimeSequenceId)id));
    //private static final IdGenerator<TimeSequenceId> TIME_SEQUENCE_ID_ID_GENERATOR = LONG_TIME_SEQUENCE_ID_ID_GENERATOR::generate;
    private static final IdGenerator<String> STRING_TIME_SEQUENCE_ID_ID_GENERATOR = withMapper(timeSequenceIdFoPaR().getFormatter());

    TimeSequenceIdGeneratorFactory(@SuppressWarnings("unused") Never never) {
        neverNeverCalled();
    }

    public static IdGenerator<String> withStringPrefixAndFormattedId(String prefix) {
        Function<TimeSequenceId, String> mapper = timeSequenceIdFoPaR().getFormatter().andThen(prefix::concat);
        return withMapper(mapper);
    }

    public static <F> IdGenerator<F> withMapper(Function<TimeSequenceId, F> mapper) {
        Supplier<F> supplier = SupplierUtils.map(timeSequenceIdGenerator(), mapper);
        return supplier::get;
    }

    public static IdGenerator<String> stringIdGenerator() {
        return STRING_TIME_SEQUENCE_ID_ID_GENERATOR;
    }

    public static IdGenerator<TimeSequenceId> timeSequenceIdGenerator() {
        return TimeSequenceIdGenerator.timeSequenceIdGenerator();
    }
}
