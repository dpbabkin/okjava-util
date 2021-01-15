package okjava.util.id.format;

import okjava.util.forper.ABForPer;
import okjava.util.forper.ForPer;

import static okjava.util.id.format.LongTimeSequenceIdFormatter.longTimeSequenceIdFormatter;

/**
 * @author Dmitry Babkin dpbabkin@gmail.com
 * 15.01.2021 19:37
 */
@Deprecated // never used.
public final class LongTimeSequenceIdForPer extends ABForPer<Long> {

    private final static ForPer<Long> INSTANCE = new LongTimeSequenceIdForPer();

    public static ForPer<Long> longTimeSequenceIdForPer() {
        return i();
    }

    public static ForPer<Long> i() {
        return INSTANCE;
    }

    private LongTimeSequenceIdForPer() {
        super(TimeSequenceIdParser.timeSequenceIdParser().getLongParser(), longTimeSequenceIdFormatter().getFormatter());
    }
}
